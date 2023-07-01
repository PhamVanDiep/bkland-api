package com.api.bkland.service;

import com.api.bkland.constant.enumeric.EType;
import com.api.bkland.entity.*;
import com.api.bkland.entity.response.*;
import com.api.bkland.payload.request.SearchRequest;
import com.api.bkland.payload.response.CountInterestAndCommentResponse;
import com.api.bkland.payload.response.RepDetailPageResponse;
import com.api.bkland.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RealEstatePostService {
    @Autowired
    private RealEstatePostRepository repository;

    @Autowired
    private RealEstatePostPriceRepository realEstatePostPriceRepository;

    @Autowired
    private InterestedRepository interestedRepository;

    @Autowired
    private PostMediaRepository postMediaRepository;

    @Autowired
    private PostViewRepository postViewRepository;

    @Autowired
    private ClickedInfoViewRepository clickedInfoViewRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private StatisticPriceFluctuationRepository statisticPriceFluctuationRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    private Logger logger = LoggerFactory.getLogger(RealEstatePostService.class);

    public RealEstatePost findById(String id) {
        Optional<RealEstatePost> realEstatePost = repository.findById(id);
        return realEstatePost.get();
    }

    public List<RealEstatePost> findByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    @Transactional
    public RealEstatePost create(RealEstatePost realEstatePost) {
        RealEstatePost saved = repository.save(realEstatePost);
        RealEstatePostPrice realEstatePostPrice = new RealEstatePostPrice();
        realEstatePostPrice.setId(0L);
        realEstatePostPrice.setPrice(saved.getPrice());
        realEstatePostPrice.setRealEstatePost(saved);
        realEstatePostPrice.setCreateBy(saved.getCreateBy());
        realEstatePostPrice.setCreateAt(Instant.now());
        realEstatePostPriceRepository.save(realEstatePostPrice);
        return saved;
    }

    @Transactional
    public void createRepPrice(Double price, String repId, String userId) {
        RealEstatePostPrice realEstatePostPrice = new RealEstatePostPrice();
        realEstatePostPrice.setId(0L);
        realEstatePostPrice.setRealEstatePost(findByIdAndEnable(repId));
        realEstatePostPrice.setCreateAt(Instant.now());
        realEstatePostPrice.setCreateBy(userId);
        realEstatePostPrice.setPrice(price);
        realEstatePostPriceRepository.save(realEstatePostPrice);
    }

    @Transactional
    public RealEstatePost update(RealEstatePost realEstatePost) {
        return repository.save(realEstatePost);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Transactional
    public void disablePostExpire() {
        try {
            repository.disablePostExpire();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    public void disablePostById(String id) {
        repository.disablePostById(id);
    }

    public List<IRepAdmin> findAll() {
        return repository.findAllByAdmin();
    }

    @Transactional
    public void updatePostStatus(String status, String id) {
        repository.updateStatus(status, id);
    }

    @Transactional
    public void updateView(String realEstatePostId) {
        repository.updateView(realEstatePostId);
        PostView postView= new PostView();
        postView.setId(0L);
        postView.setRealEstatePostId(realEstatePostId);
        postView.setCreateBy("undefined");
        postView.setCreateAt(Instant.now());
        postViewRepository.save(postView);
    }

    @Transactional
    public void updateClickedView(String realEstatePostId) {
        repository.updateClickedView(realEstatePostId);
        ClickedInfoView clickedInfoView = new ClickedInfoView();
        clickedInfoView.setId(0L);
        clickedInfoView.setRealEstatePostId(realEstatePostId);
        clickedInfoView.setCreateBy("undefined");
        clickedInfoView.setCreateAt(Instant.now());
        clickedInfoViewRepository.save(clickedInfoView);
    }

    public RealEstatePost findByIdAndEnable(String id) {
        Optional<RealEstatePost> realEstatePost = repository.findByIdAndEnable(id, true);
        if (realEstatePost.isEmpty()) {
            return null;
        }
        return realEstatePost.get();
    }

    public boolean existsByIdAndEnable(String id) {
        return repository.existsByIdAndEnable(id, true);
    }

    public List<IRepEnableRequest> enableRequestRep(String userId) {
        return repository.enableRequest(userId);
    }

    public List<IRepRequested> repRequested(String userId) {
        return repository.repRequested(userId);
    }

    public List<IRepRequested> requestedOfAgency(String agencyId) {
        return repository.requestedOfAgency(agencyId);
    }

    @Transactional
    public Interested saveInterested(Interested interested) {
        return interestedRepository.save(interested);
    }

    public List<IPostInterested> findListInterestPostsOfUser(String userId, String deviceInfo) {
        if (deviceInfo != null && deviceInfo.length() > 0 && (userId == null || userId.length() == 0)) {
            return interestedRepository.findRepDetailByDeviceInfo(deviceInfo);
        }
        return interestedRepository.findRepDetailByUserId(userId);
    }

    @Transactional
    public void deleteInterested(Long id) {
        interestedRepository.deleteById(id);
    }

    public Optional<Interested> findByDeviceInfoAndRealEstatePostId(String deviceInfo, String realEstatePostId) {
        return interestedRepository.findByDeviceInfoAndRealEstatePostId(deviceInfo, realEstatePostId);
    }

    public Optional<Interested> findByUserIdAndRealEstatePostId(String userId, String realEstatePostId) {
        return interestedRepository.findByUserIdAndRealEstatePostId(userId, realEstatePostId);
    }

    public Object findContact(String id) {
        Optional<IEnableUserChat> optional = repository.findContact(id);
        if (optional.isEmpty()) {
            Optional<IEnableUserChat> ownerOptional = repository.findOwnerContact(id);
            if (ownerOptional.isEmpty()) {
                return null;
            }
            return ownerOptional.get();
        } else {
            return optional.get();
        }
    }

    public boolean isInterested(String userId, String realEstatePostId, String deviceInfo) {
        if (deviceInfo != null && deviceInfo.length() > 0 && (userId == null || userId.length() == 0)) {
            return interestedRepository.existsByDeviceInfoAndRealEstatePostId(deviceInfo, realEstatePostId);
        }
        return interestedRepository.existsByUserIdAndRealEstatePostId(userId, realEstatePostId);
    }

    public Integer countInterested(String userId, String deviceInfo) {
        if (deviceInfo != null && deviceInfo.length() > 0 && (userId == null || userId.length() == 0)) {
            return interestedRepository.countByUserIdAndDeviceInfo("anonymous", deviceInfo);
        }
        return interestedRepository.countByUserId(userId);
    }

    public List<RepDetailPageResponse> detailPageData(Byte sell, String type, Integer limit, Integer offset, String userId, String deviceInfo) {
        List<RepDetailPageResponse> responses = new ArrayList<>();
        List<String> repIds = repository.getRepIdDetailPage(sell, type, limit, offset);
        if (repIds.isEmpty()) {
            return responses;
        }
        repIds
                .stream()
                .forEach(e -> {
                    RepDetailPageResponse response = new RepDetailPageResponse();
                    Optional<RealEstatePost> realEstatePostOptional = repository.findById(e);
                    if (!realEstatePostOptional.isEmpty()) {
                        RealEstatePost realEstatePost = realEstatePostOptional.get();
                        response.setArea(realEstatePost.getArea());
                        response.setAddressShow(realEstatePost.getAddressShow());
                        response.setDescription(realEstatePost.getDescription());
                        response.setId(e);
                        response.setTitle(realEstatePost.getTitle());
                        response.setPrice(realEstatePost.getPrice());
                        response.setCreateAt(realEstatePost.getCreateAt());
                        response.setAvatarUrl(realEstatePost.getOwnerId().getAvatarUrl());
                        response.setFullName(realEstatePost.getOwnerId().getFirstName() + " "
                                + realEstatePost.getOwnerId().getMiddleName() + " "
                                + realEstatePost.getOwnerId().getLastName()
                        );
                        response.setPhoneNumber(realEstatePost.getOwnerId().getPhoneNumber());
                        List<PostMedia> postMedias = postMediaRepository.findByPostId(e);
                        if (!postMedias.isEmpty()) {
                            response.setImageUrl(postMedias.get(0).getId());
                        }
                        response.setInterested(isInterested(userId, e, deviceInfo));
                        responses.add(response);
                    }
                });
        return responses;
    }

    public Object findByMostInterested() {
//        List<RepClientResponse> responses = new ArrayList<>();
//        List<IRepClient> repClients = repository.getLstMostInterested();
//        repClients
//                .stream()
//                .forEach(e -> {
//                    Optional<String> imageUrlOptional = postMediaRepository.getOneImageOfPost(e.getId());
//                    RepClientResponse response = new RepClientResponse(e.getId(),
//                            e.getTitle(),
//                            e.getPrice(),
//                            e.getArea(),
//                            e.getSell(),
//                            e.getAddressShow(),
//                            e.getCreateAt(),
//                            imageUrlOptional.isEmpty() ? "" : imageUrlOptional.get());
//                    responses.add(response);
//                });
//        return responses;
        return repository.getLstMostInterested();
    }

    public Object findByMostView() {
//        List<RepClientResponse> responses = new ArrayList<>();
//        List<IRepClient> repClients = repository.getLstMostView();
//        repClients
//                .stream()
//                .forEach(e -> {
//                    Optional<String> imageUrlOptional = postMediaRepository.getOneImageOfPost(e.getId());
//                    RepClientResponse response = new RepClientResponse(e.getId(),
//                            e.getTitle(),
//                            e.getPrice(),
//                            e.getArea(),
//                            e.getSell(),
//                            e.getAddressShow(),
//                            e.getCreateAt(),
//                            imageUrlOptional.isEmpty() ? "" : imageUrlOptional.get());
//                    responses.add(response);
//                });
//        return responses;
        return repository.getLstMostView();
    }

    public Integer countTotalBySellAndTypeClient(Byte sell, String type) {
        return repository.countTotalBySellAndTypeClient(sell, type);
    }

    public CountInterestAndCommentResponse countNoOfInterestAndComment(String postId) {
        CountInterestAndCommentResponse response = new CountInterestAndCommentResponse();
        response.setNoOfComment(postCommentRepository.countByPostId(postId));
        response.setNoOfInterest(interestedRepository.countByRealEstatePostId(postId));
        return response;
    }

    public Object search(SearchRequest request) {
        String query = "select rep.id, rep.title, rep.address_show as addressShow, " +
                "rep.price, rep.area, rep.is_sell as sell, rep.create_at as createAt, rep.description, " +
                "concat(u.first_name, ' ', u.middle_name, ' ', u.last_name) as fullName, " +
                "u.phone_number as phoneNumber, u.avatar_url as avatarUrl, ";
        if (request.getDeviceInfo() != null && request.getDeviceInfo().length() > 0) {
            query += "(select count(*) > 0 from interested where user_id = :userId and device_info = :deviceInfo and real_estate_post_id = rep.id ) as interested, ";
        } else {
            query += "(select count(*) > 0 from interested where user_id = :userId and real_estate_post_id = rep.id ) as interested, ";
        }
        query += "(select id from post_media where post_id = rep.id limit 1) as imageUrl\n" +
                "from real_estate_post rep inner join user u on u.id = rep.owner_id ";
        if (request.getType() != null) {
            if (request.getType().equals(EType.APARTMENT.toString())) {
                query += "inner join apartment spc on spc.real_estate_post_id = rep.id ";
            } else if (request.getType().equals(EType.HOUSE.toString())) {
                query += "inner join house spc on spc.real_estate_post_id = rep.id ";
            }
        }
        query += "where u.enable = 1 " +
                "and rep.enable = 1 " +
                "and rep.status = 'DA_KIEM_DUYET' " +
                "and datediff(now(), rep.create_at) <= rep.period ";
        if (request.getSell() != null) {
            if (request.getSell()) {
                query += "and rep.is_sell = 1 ";
            } else {
                query += "and rep.is_sell = 0 ";
            }
        }
        if (request.getType() != null) {
            query += "and rep.type = '" + request.getType().replaceAll("\\s+", "") + "' ";
            if (!request.getType().equals(EType.PLOT.toString()) && request.getNoOfBedrooms() != null && request.getNoOfBedrooms().length > 0) {
                int index = 0;
                for (Integer noOfBedroom: request.getNoOfBedrooms()) {
                    if (index == 0) {
                        if (noOfBedroom > 5) {
                            query += "and ( spc.no_bedroom >= " + noOfBedroom + " ";
                        } else {
                            query += "and ( spc.no_bedroom = " + noOfBedroom + " ";
                        }
                    } else {
                        if (noOfBedroom > 5) {
                            query += "or spc.no_bedroom >= " + noOfBedroom + " ";
                        } else {
                            query += "or spc.no_bedroom = " + noOfBedroom + " ";
                        }
                    }
                    index ++;
                }
                query += " ) ";
            }
        }
        if (request.getProvinceCode() != null) {
            query += "and rep.province_code = '" + request.getProvinceCode().replaceAll("\\s+", "") + "' ";
        }
        if (request.getDistrictCode() != null && request.getDistrictCode().length > 0) {
            int index = 0;
            for (String districtCode: request.getDistrictCode()) {
                if (index == 0) {
                    query += "and ( rep.district_code = '" + districtCode.replaceAll("\\s+", "") + "' ";
                } else {
                    query += "or rep.district_code = '" + districtCode.replaceAll("\\s+", "") + "' ";
                }
                index ++;
            }
            query += " ) ";
        }
        if (request.getWardCode() != null && request.getWardCode().length > 0) {
            int index = 0;
            for (String wardCode: request.getWardCode()) {
                if (index == 0) {
                    query += "and ( rep.ward_code = '" + wardCode.replaceAll("\\s+", "") + "' ";
                } else {
                    query += "or rep.ward_code = '" + wardCode.replaceAll("\\s+", "") + "' ";
                }
                index ++;
            }
            query += " ) ";
        }
        if (request.getStartPrice() != null) {
            if (request.getEndPrice() != null) {
                query += "and rep.price >= " + request.getStartPrice() + " and rep.price <= " + request.getEndPrice() + " ";
            } else {
                query += "and rep.price >= " + request.getStartPrice() + " ";
            }
        } else {
            if (request.getEndPrice() != null) {
                query += "and rep.price <= " + request.getEndPrice() + " ";
            }
        }
        if (request.getStartArea() != null) {
            if (request.getEndArea() != null) {
                query += "and rep.area >= " + request.getStartArea() + " and rep.area <= " + request.getEndArea() + " ";
            } else {
                query += "and rep.area >= " + request.getStartArea() + " ";
            }
        } else {
            if (request.getEndArea() != null) {
                query += "and rep.area <= " + request.getEndArea() + " ";
            }
        }
        if (request.getDirection() != null && request.getDirection().length > 0) {
            int index = 0;
            for (String direction: request.getDirection()) {
                if (index == 0) {
                    query += "and ( rep.direction = '" + direction.replaceAll("\\s+", "") + "' ";
                } else {
                    query += "or rep.direction = '" + direction.replaceAll("\\s+", "") + "' ";
                }
                index ++;
            }
            query += " ) ";
        }
        if (request.getKeyword() != null && request.getKeyword().replaceAll("\\s", "").length() > 0) {
            String[] words = request.getKeyword().split("\\s+");
            int noOfWords = 0;
            for (String item: words) {
                String word = item.toUpperCase();
                if (noOfWords == 0) {
                    query += "and (UPPER(rep.title) like '%" + word + "%' or UPPER(rep.description) like '%" + word + "%' ";
                } else {
                    query += "or UPPER(rep.title) like '%" + word + "%' or UPPER(rep.description) like '%" + word + "%' ";
                }
                noOfWords ++;
            }
            query += ") ";
        }
        query += "order by rep.priority desc limit :limit offset :offset";

        // parameters
        Map<String, Object> params = new HashMap<>();
        params.put("userId", request.getUserId());
        if (request.getDeviceInfo() != null && request.getDeviceInfo().length() > 0) {
            params.put("deviceInfo", request.getDeviceInfo());
        }
        params.put("limit", request.getLimit());
        params.put("offset", request.getOffset());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);

        List<Map<String, Object>> response = jdbcTemplate.queryForList(query, sqlParameterSource);
        return response;
    }

    @Transactional
    public void calculatePricePerAreaUnit(String ngay) {
//        List<String> provinceCodes = statisticPriceFluctuationRepository.getAllProvinceCodes();
        String HaNoiCode = "01";
        List<District> districtCodes = districtRepository.findByProvinceCode(HaNoiCode);
        districtCodes
                .stream()
                .parallel()
                .forEach(e -> {
                    List<Ward> wardCodes = wardRepository.findByDistrictCode(e.getCode());
                    wardCodes
                            .stream()
                            .parallel()
                                    .forEach(ee -> {
                                        StatisticPriceFluctuation sellHouse = getStatisticPriceFluctuation(true, EType.HOUSE, HaNoiCode, e.getCode(), ee.getCode(), ngay);
                                        StatisticPriceFluctuation sellApartment = getStatisticPriceFluctuation(true, EType.APARTMENT, HaNoiCode, e.getCode(), ee.getCode(), ngay);
                                        StatisticPriceFluctuation sellPlot = getStatisticPriceFluctuation(true, EType.PLOT, HaNoiCode, e.getCode(), ee.getCode(), ngay);
                                        StatisticPriceFluctuation hireHouse = getStatisticPriceFluctuation(false, EType.HOUSE, HaNoiCode, e.getCode(), ee.getCode(), ngay);
                                        StatisticPriceFluctuation hireApartment = getStatisticPriceFluctuation(false, EType.APARTMENT, HaNoiCode, e.getCode(), ee.getCode(), ngay);
                                        if (sellHouse != null) {
                                            statisticPriceFluctuationRepository.save(sellHouse);
                                        }
                                        if (sellApartment != null) {
                                            statisticPriceFluctuationRepository.save(sellApartment);
                                        }
                                        if (sellPlot != null) {
                                            statisticPriceFluctuationRepository.save(sellPlot);
                                        }
                                        if (hireHouse != null) {
                                            statisticPriceFluctuationRepository.save(hireHouse);
                                        }
                                        if (hireApartment != null) {
                                            statisticPriceFluctuationRepository.save(hireApartment);
                                        }
                                    });
                });
    }

    private StatisticPriceFluctuation getStatisticPriceFluctuation(boolean sell, EType type, String provinceCode, String districtCode, String wardCode, String ngay) {
        String query = "select avg(price/area) as result from real_estate_post \n" +
                "where enable = 1 \n" +
                "and (status = 'DA_KIEM_DUYET' or status = 'DA_HOAN_THANH') \n" +
                "and datediff(now(), create_at) <= period\n" +
                "and is_sell = :sell\n" +
                "and type = :type\n" +
                "and district_code = :districtCode\n" +
                "and province_code = :provinceCode\n" +
                "and ward_code = :wardCode\n" +
                "and (date(create_at) = :ngay " +
                "or date(update_at) = :ngay)";
        Map<String, Object> params = new HashMap<>();
        params.put("sell", sell ? 1 : 0);
        params.put("type", type.toString());
        params.put("districtCode", districtCode);
        params.put("provinceCode", provinceCode);
        params.put("wardCode", wardCode);
        params.put("ngay", ngay);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        Map<String, Object> response = jdbcTemplate.queryForMap(query, sqlParameterSource);
        if (response.get("result") == null) {
            return null;
        } else {
            StatisticPriceFluctuation statisticPriceFluctuation = new StatisticPriceFluctuation();
            statisticPriceFluctuation.setId(0L);
            statisticPriceFluctuation.setSell(sell);
            statisticPriceFluctuation.setType(type);
            try {
                statisticPriceFluctuation.setCreateAt(new SimpleDateFormat("yyyy-MM-dd").parse(ngay));
            } catch (ParseException e) {
                e.printStackTrace();
                statisticPriceFluctuation.setCreateAt(new Date());
            }
            statisticPriceFluctuation.setDistrictCode(districtCode);
            statisticPriceFluctuation.setProvinceCode(provinceCode);
            statisticPriceFluctuation.setWardCode(wardCode);
            statisticPriceFluctuation.setPrice((Double) response.get("result"));
            return statisticPriceFluctuation;
        }
//        statisticPriceFluctuation.setPrice(repository.calculatePricePerAreaUnit(sell ? 1 : 0, type.toString(), districtCode, provinceCode, wardCode));
    }
}

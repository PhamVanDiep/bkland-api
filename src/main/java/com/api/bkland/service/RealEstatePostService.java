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
        String query = "select rep.id, rep.title, rep.address_show as addressShow, rep.price, rep.area, rep.is_sell as sell, rep.create_at as createAt, rep.description, " +
                "(select id from post_media where post_id = rep.id limit 1) as imageUrl\n" +
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
            if (!request.getType().equals(EType.PLOT.toString()) && request.getNoOfBedrooms() != null) {
                if (request.getNoOfBedrooms() > 5) {
                    query += "and spc.no_bedroom >= " + request.getNoOfBedrooms() + " ";
                } else {
                    query += "and spc.no_bedroom = " + request.getNoOfBedrooms() + " ";
                }
            }
        }
        if (request.getProvinceCode() != null) {
            query += "and rep.province_code = '" + request.getProvinceCode().replaceAll("\\s+", "") + "' ";
        }
        if (request.getDistrictCode() != null) {
            query += "and rep.district_code = '" + request.getDistrictCode().replaceAll("\\s+", "") + "' ";
        }
        if (request.getWardCode() != null) {
            query += "and rep.ward_code = '" + request.getWardCode().replaceAll("\\s+", "") + "' ";
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
        if (request.getDirection() != null) {
            query += "and rep.direction = '" + request.getDirection().replaceAll("\\s+", "") + "' ";
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
        params.put("limit", request.getLimit());
        params.put("offset", request.getOffset());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);

        List<Map<String, Object>> response = jdbcTemplate.queryForList(query, sqlParameterSource);
        return response;
    }
}

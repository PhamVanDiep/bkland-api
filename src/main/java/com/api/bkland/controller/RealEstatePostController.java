package com.api.bkland.controller;

import com.api.bkland.config.annotation.CurrentUser;
import com.api.bkland.constant.Message;
import com.api.bkland.constant.PayContent;
import com.api.bkland.constant.enumeric.ERole;
import com.api.bkland.constant.enumeric.EStatus;
import com.api.bkland.constant.enumeric.EType;
import com.api.bkland.entity.*;
import com.api.bkland.payload.dto.InterestedDTO;
import com.api.bkland.payload.dto.PostMediaDTO;
import com.api.bkland.payload.dto.post.*;
import com.api.bkland.payload.request.ClickedUserInfo;
import com.api.bkland.payload.request.ListImageUpload;
import com.api.bkland.payload.request.RealEstatePostRequest;
import com.api.bkland.payload.request.UpdatePostStatusRequest;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.payload.response.RealEstatePostResponse;
import com.api.bkland.security.services.UserDetailsImpl;
import com.api.bkland.service.*;
import com.api.bkland.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RealEstatePostController {
    @Autowired
    private RealEstatePostService service;

    @Autowired
    private PlotService plotService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostMediaService postMediaService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostPayService postPayService;

    @Autowired
    private SpecialAccountService specialAccountService;

    @Autowired
    private NotifyService notifyService;

    @GetMapping("/api/no-auth/real-estate-post/{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable("id") String id) {
        try {
            if (!service.existsByIdAndEnable(id)) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            List<String> strings = Arrays.asList(id.split("-"));
            String type = strings.get(0);
            BasePost basePost = null;
            if (type.equalsIgnoreCase(EType.PLOT.toString())) {
                Plot plot = plotService.findByRealEstatePostId(id);
                if (plot != null) {
                    basePost = modelMapper.map(plot, PlotDTO.class);
                }
            } else if (type.equalsIgnoreCase(EType.APARTMENT.toString())) {
                Apartment apartment = apartmentService.findByRealEstatePostId(id);
                if (apartment != null) {
                    basePost = modelMapper.map(apartment, ApartmentDTO.class);
                }
            } else if (type.equalsIgnoreCase(EType.HOUSE.toString())) {
                House house = houseService.findByRealEstatePostId(id);
                if (house != null) {
                    basePost = modelMapper.map(house, HouseDTO.class);
                }
            }
            if (basePost == null) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            List<PostMedia> postMedia = postMediaService.findByPostId(id);

            return ResponseEntity.ok(new BaseResponse(
                    new RealEstatePostResponse(
                            basePost,
                            postMedia.stream().map(e -> modelMapper.map(e, PostMediaDTO.class)).collect(Collectors.toList())),
                    "",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy thông tin bài đăng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/api/no-auth/real-estate-post/user-view/{id}")
    public ResponseEntity<BaseResponse> findByIdWithIncreaseView(@PathVariable("id") String id) {
        try {
            if (!service.existsByIdAndEnable(id)) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            List<String> strings = Arrays.asList(id.split("-"));
            String type = strings.get(0);
            BasePost basePost = null;
            if (type.equalsIgnoreCase(EType.PLOT.toString())) {
                Plot plot = plotService.findByRealEstatePostId(id);
                if (plot != null) {
                    basePost = modelMapper.map(plot, PlotDTO.class);
                }
            } else if (type.equalsIgnoreCase(EType.APARTMENT.toString())) {
                Apartment apartment = apartmentService.findByRealEstatePostId(id);
                if (apartment != null) {
                    basePost = modelMapper.map(apartment, ApartmentDTO.class);
                }
            } else if (type.equalsIgnoreCase(EType.HOUSE.toString())) {
                House house = houseService.findByRealEstatePostId(id);
                if (house != null) {
                    basePost = modelMapper.map(house, HouseDTO.class);
                }
            }
            if (basePost == null) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            List<PostMedia> postMedia = postMediaService.findByPostId(id);
            service.updateView(id);
            return ResponseEntity.ok(new BaseResponse(
                    new RealEstatePostResponse(
                            basePost,
                            postMedia.stream().map(e -> modelMapper.map(e, PostMediaDTO.class)).collect(Collectors.toList())),
                    "",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy thông tin bài đăng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/api/no-auth/real-estate-post/click-info")
    public ResponseEntity<BaseResponse> clickUserDetail(@RequestBody ClickedUserInfo body) {
        try {
            if (!service.existsByIdAndEnable(body.getPostId())) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            service.updateClickedView(body.getPostId());
            return ResponseEntity.ok(new BaseResponse(null, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy thông tin người đăng bài. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/api/v1/real-estate-post")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> createRealEstatePost(@RequestBody RealEstatePostRequest request, @CurrentUser UserDetailsImpl userDetails) {
        try {
            RealEstatePostDTO realEstatePostDTO = request.getRealEstatePost();
            User user = userService.findById(realEstatePostDTO.getOwnerId().getId());
            Role role = new Role();
            role.setId(1);
            role.setName(ERole.ROLE_USER);
            if (!agencyPeriodPriority(user, request.getRealEstatePost().getDistrict().getCode())
                    && user.getRoles().contains(role)) {
                if (user.getAccountBalance() < Util.calculatePostPrice(realEstatePostDTO.getPriority(), realEstatePostDTO.getPeriod(), realEstatePostDTO.isSell())) {
                    return ResponseEntity.ok(new BaseResponse(null,
                            "Số dư trong tài khoản không đủ để thực hiện giao dịch.",
                            HttpStatus.INTERNAL_SERVER_ERROR));
                }
            }
            realEstatePostDTO.setCreateAt(Instant.now());
            realEstatePostDTO.setCreateBy(userDetails.getId());
            RealEstatePost realEstatePost = modelMapper.map(realEstatePostDTO, RealEstatePost.class);
            service.create(realEstatePost);

            if (realEstatePostDTO.getType().equals(EType.PLOT)) {
                Plot plotEntity = modelMapper.map(request.getPlot(), Plot.class);
                plotEntity.setRealEstatePost(realEstatePost);
                plotService.create(plotEntity);
            } else if (realEstatePostDTO.getType().equals(EType.APARTMENT)) {
                Apartment apartmentEntity = modelMapper.map(request.getApartment(), Apartment.class);
                apartmentEntity.setRealEstatePost(realEstatePost);
                apartmentService.create(apartmentEntity);
            } else if (realEstatePostDTO.getType().equals(EType.HOUSE)) {
                House houseEntity = modelMapper.map(request.getHouse(), House.class);
                houseEntity.setRealEstatePost(realEstatePost);
                houseService.create(houseEntity);
            }
            List<PostMediaDTO> postMediaDTOS = request.getImages();

            for (PostMediaDTO postMediaDTO: postMediaDTOS) {
                postMediaService.save(modelMapper.map(postMediaDTO, PostMedia.class));
            }
            if (!agencyPeriodPriority(user, request.getRealEstatePost().getDistrict().getCode())
                && user.getRoles().contains(role)) {
                int pay = Util.calculatePostPrice(realEstatePostDTO.getPriority(), realEstatePostDTO.getPeriod(), realEstatePostDTO.isSell());
                if (pay > 0) {
                    PostPay postPay = new PostPay();
                    postPay.setId(0L);
                    postPay.setUser(user);
                    postPay.setRealEstatePost(realEstatePost);
                    postPay.setPrice(pay);
                    postPay.setContent(PayContent.POST_PAY);
                    postPay.setAccountBalance(user.getAccountBalance() - pay);
                    postPay.setCreateAt(Instant.now());
                    this.postPayService.createPostPay(postPay);

                    user.setAccountBalance(user.getAccountBalance() - pay);
                    user.setUpdateAt(Instant.now());
                    user.setUpdateBy(user.getId());
                    userService.updateUserInfo(user);
                }
            }
            notifyService.notifyToAdmin(Message.NEW_REP_ADMIN);
            return ResponseEntity.ok(new
                    BaseResponse(null,
                    "Đã tạo bài viết thành công. Chờ quản trị viên kiểm duyệt",
                    HttpStatus.OK));
        } catch (Exception e) {
            service.deleteById(request.getRealEstatePost().getId());
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi tạo bài đăng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private boolean agencyPeriodPriority(User user, String districtCode) {
        Role role = new Role();
        role.setId(2);
        role.setName(ERole.ROLE_AGENCY);

        if (user.getRoles().contains(role)) {
            List<String> districtCodes = specialAccountService.getAllDistrictCodeOfAgency(user.getId());
            if (districtCodes.stream().anyMatch(s -> s.equals(districtCode))) {
                return true;
            }
        }
        return false;
    }
    
    @PutMapping("/api/v1/real-estate-post")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> updatePost(@RequestBody RealEstatePostRequest request, @CurrentUser UserDetailsImpl userDetails) {
        try {
            if (!service.existsByIdAndEnable(request.getRealEstatePost().getId())) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            RealEstatePost realEstatePostDB = service.findByIdAndEnable(request.getRealEstatePost().getId());

            RealEstatePostDTO realEstatePostDTO = request.getRealEstatePost();
            realEstatePostDTO.setUpdateAt(Instant.now());
            realEstatePostDTO.setUpdateBy(userDetails.getId());
            RealEstatePost realEstatePost = modelMapper.map(realEstatePostDTO, RealEstatePost.class);
            service.update(realEstatePost);
            if (realEstatePostDTO.getType().equals(EType.PLOT)) {
                Plot plotEntity = modelMapper.map(request.getPlot(), Plot.class);
                plotEntity.setRealEstatePost(realEstatePost);
                plotService.update(plotEntity);
            } else if (realEstatePostDTO.getType().equals(EType.APARTMENT)) {
                Apartment apartmentEntity = modelMapper.map(request.getApartment(), Apartment.class);
                apartmentEntity.setRealEstatePost(realEstatePost);
                apartmentService.update(apartmentEntity);
            } else if (realEstatePostDTO.getType().equals(EType.HOUSE)) {
                House houseEntity = modelMapper.map(request.getHouse(), House.class);
                houseEntity.setRealEstatePost(realEstatePost);
                houseService.update(houseEntity);
            }
            List<PostMediaDTO> postMediaDTOS = request.getImages();
            if (!postMediaDTOS.isEmpty()) {
                for (PostMediaDTO postMediaDTO :
                        postMediaDTOS) {
                    postMediaService.save(modelMapper.map(postMediaDTO, PostMedia.class));
                }
            }

            if (realEstatePostDB.getPrice() != request.getRealEstatePost().getPrice()) {
                service.createRepPrice(request.getRealEstatePost().getPrice(), request.getRealEstatePost().getId(), userDetails.getId());
                notifyService.notifyAgencyREPUpdate(Message.CAP_NHAT_REP, realEstatePostDTO.getDistrict().getCode());
                notifyService.notifyInterested(Message.getCAP_NHAT_REP_INTERESTED(realEstatePostDTO.getTitle()), realEstatePostDTO.getId());
            }

            return ResponseEntity.ok(new
                    BaseResponse(null,
                    "Đã cập nhật bài viết thành công.",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi cập nhật bài đăng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/api/v1/real-estate-post/user/{ownerId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> findByOwnerId(@PathVariable("ownerId") String ownerId) {
        try {
            List<RealEstatePost> realEstatePosts = service.findByOwnerId(ownerId);
            return ResponseEntity.ok(new BaseResponse(
                    realEstatePosts.stream().map(e -> modelMapper.map(e, RealEstatePostDTO.class)).collect(Collectors.toList()),
                    "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng của người dùng. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/api/v1/real-estate-post/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> findAll() {
        try {
            List<RealEstatePost> realEstatePosts = this.service.findAll();
            if (realEstatePosts.isEmpty()) {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Không tìm thấy bài đăng nào.",
                        HttpStatus.NO_CONTENT));
            }
            return ResponseEntity.ok(new BaseResponse(
                    realEstatePosts.stream().map(e -> modelMapper.map(e, RealEstatePostDTO.class)).collect(Collectors.toList()),
                    "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/api/v1/real-estate-post/disable/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> disablePostById(@PathVariable("id") String id) {
        try {
            if (!service.existsByIdAndEnable(id)) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            service.disablePostById(id);
            return ResponseEntity.ok(new BaseResponse(null, "Ẩn bài viết thành công", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi ẩn bài đăng. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/api/v1/real-estate-post/enable/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> disableOrEnablePostById(@PathVariable("id") String id) {
        try {
            RealEstatePost realEstatePost = service.findById(id);
            if (realEstatePost.isEnable()) {
                realEstatePost.setEnable(false);
                service.update(realEstatePost);
                return ResponseEntity.ok(new BaseResponse(0, "Ẩn bài viết thành công", HttpStatus.OK));
            } else {
                realEstatePost.setEnable(true);
                service.update(realEstatePost);
                return ResponseEntity.ok(new BaseResponse(1, "Hiện bài viết thành công", HttpStatus.OK));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi ẩn / hiện bài đăng. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/api/v1/real-estate-post/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> updateStatus(@RequestBody UpdatePostStatusRequest request) {
        try {
            if (!service.existsByIdAndEnable(request.getPostId())) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            service.updatePostStatus(request.getStatus().toString(), request.getPostId());
            RealEstatePost realEstatePost = service.findById(request.getPostId());
            if (request.getStatus().equals(EStatus.BI_TU_CHOI)) {
                notifyService.notifyAcceptRejectREP(
                        "Bài viết " + realEstatePost.getTitle() + " đã bị quản trị viên từ chối",
                        realEstatePost.getOwnerId().getId());
            } else if (request.getStatus().equals(EStatus.DA_KIEM_DUYET)) {
                notifyService.notifyAcceptRejectREP(
                        "Bài viết " + realEstatePost.getTitle() + " đã được quản trị viên chấp nhận",
                        realEstatePost.getOwnerId().getId());
                notifyService.notifyAgencyREPUpdate(Message.TAO_REP, realEstatePost.getDistrict().getCode());
            }
            return ResponseEntity.ok(new BaseResponse(
                    request.getStatus().toString(),
                    "Cập nhật trạng thái bài viết thành công.",
                    HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi ẩn / hiện bài đăng. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/api/v1/real-estate-post/enable-request")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<BaseResponse> enableRequest(@CurrentUser UserDetailsImpl userDetails) {
        try {
            return ResponseEntity.ok(new BaseResponse(service.enableRequestRep(userDetails.getId()), "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng đủ điều kiện nhờ môi giới giúp đỡ.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/api/v1/real-estate-post/user-requested")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<BaseResponse> userRequested(@CurrentUser UserDetailsImpl userDetails) {
        try {
            return ResponseEntity.ok(new BaseResponse(service.repRequested(userDetails.getId()), "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng đã nhờ môi giới giúp đỡ của người dùng.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/api/v1/real-estate-post/agency-requested")
    @PreAuthorize("hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> agencyRequested(@CurrentUser UserDetailsImpl userDetails) {
        try {
            if (!specialAccountService.isAgency(userDetails.getId())) {
                return ResponseEntity.ok(new BaseResponse(null, "Người dùng không phải là môi giới.", HttpStatus.NOT_ACCEPTABLE));
            }
            return ResponseEntity.ok(new BaseResponse(service.requestedOfAgency(userDetails.getId()), "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng đã nhờ môi giới giúp đỡ.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/api/no-auth/real-estate-post/interested")
    public ResponseEntity<BaseResponse> anonymousInterested(@RequestBody InterestedDTO body) {
        try {
            if (!service.existsByIdAndEnable(body.getRealEstatePostId())) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            Optional<Interested> interestedOptional = service.findByDeviceInfoAndRealEstatePostId(body.getDeviceInfo(), body.getRealEstatePostId());
            if (interestedOptional.isEmpty()) {
                body.setCreateBy("anonymous");
                body.setCreateAt(Instant.now());
                body.setId(0L);
                body.setUserId("anonymous");
                Interested interested = modelMapper.map(body, Interested.class);
                return ResponseEntity.ok(new BaseResponse(
                        modelMapper.map(service.saveInterested(interested), InterestedDTO.class),
                        "", HttpStatus.OK
                ));
            } else {
                service.deleteInterested(interestedOptional.get().getId());
                return ResponseEntity.ok(new BaseResponse(interestedOptional.get().getId(), "DELETED", HttpStatus.OK));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lưu thông tin quan tâm bài đăng.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @PostMapping("/api/v1/real-estate-post/interested")
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> userInterested(@RequestBody InterestedDTO body, @CurrentUser UserDetailsImpl userDetails) {
        try {
            if (!service.existsByIdAndEnable(body.getRealEstatePostId())) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            Optional<Interested> interestedOptional = service.findByUserIdAndRealEstatePostId(userDetails.getId(), body.getRealEstatePostId());
            if (interestedOptional.isEmpty()) {
                body.setCreateBy(userDetails.getId());
                body.setCreateAt(Instant.now());
                body.setId(0L);
                body.setUserId(userDetails.getId());
                Interested interested = modelMapper.map(body, Interested.class);
                return ResponseEntity.ok(new BaseResponse(
                        modelMapper.map(service.saveInterested(interested), InterestedDTO.class),
                        "", HttpStatus.OK
                ));
            } else {
                service.deleteInterested(interestedOptional.get().getId());
                return ResponseEntity.ok(new BaseResponse(interestedOptional.get().getId(), "DELETED", HttpStatus.OK));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lưu thông tin quan tâm bài đăng.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/api/no-auth/real-estate-post/interested")
    public ResponseEntity<BaseResponse> findByUserIdAndDeviceInfo(@RequestParam("userId") String userId, @RequestParam("deviceInfo") String deviceInfo) {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    service.findListInterestPostsOfUser(userId, deviceInfo), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng đã quan tâm của người dùng.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/api/no-auth/real-estate-post/interested/count")
    public ResponseEntity<BaseResponse> countByUserIdAndDeviceInfo(@RequestParam("userId") String userId, @RequestParam("deviceInfo") String deviceInfo) {
        try {
            return ResponseEntity.ok(new BaseResponse(service.countInterested(userId, deviceInfo), "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng đã quan tâm của người dùng.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/api/no-auth/real-estate-post/contact")
    public ResponseEntity<BaseResponse> findContactOfPost(@RequestParam String id) {
        try {
            Object response = service.findContact(id);
            if (response == null) {
                return ResponseEntity.ok(new BaseResponse(
                        null,
                        "Không tìm thấy thông tin liên lạc của bài viết.",
                        HttpStatus.NO_CONTENT
                ));
            }
            return ResponseEntity.ok(new BaseResponse(response, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy thông tin liên lạc của bài viết. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/api/no-auth/real-estate-post/isInterested")
    public ResponseEntity<BaseResponse> isInterested(@RequestParam("userId") String userId,
                                                     @RequestParam("deviceInfo") String deviceInfo,
                                                     @RequestParam("realEstatePostId") String realEstatePostId) {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    service.isInterested(userId, realEstatePostId, deviceInfo), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy thông tin người dùng quan tâm của bài viết.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }
}

package com.api.bkland.controller;

import com.api.bkland.constant.enumeric.ERole;
import com.api.bkland.constant.enumeric.EType;
import com.api.bkland.entity.*;
import com.api.bkland.payload.dto.PostMediaDTO;
import com.api.bkland.payload.dto.post.*;
import com.api.bkland.payload.request.ListImageUpload;
import com.api.bkland.payload.request.RealEstatePostRequest;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.payload.response.RealEstatePostResponse;
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

    @GetMapping("/api/no-auth/real-estate-post/{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable("id") String id) {
        try {
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

    @PostMapping("/api/v1/real-estate-post")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> createRealEstatePost(@RequestBody RealEstatePostRequest request) {
        try {
            RealEstatePostDTO realEstatePostDTO = request.getRealEstatePost();
            User user = userService.findById(realEstatePostDTO.getOwnerId().getId());
            Role role = new Role();
            role.setId(1);
            role.setName(ERole.ROLE_USER);
            if (user.getRoles().contains(role)) {
                if (user.getAccountBalance() < Util.calculatePostPrice(realEstatePostDTO.getPriority(), realEstatePostDTO.getPeriod(), realEstatePostDTO.isSell())) {
                    return ResponseEntity.ok(new BaseResponse(null,
                            "Số dư trong tài khoản không đủ để thực hiện giao dịch.",
                            HttpStatus.INTERNAL_SERVER_ERROR));
                }
            }
            realEstatePostDTO.setCreateAt(Instant.now());
            RealEstatePost realEstatePost = modelMapper.map(realEstatePostDTO, RealEstatePost.class);
            service.create(realEstatePost);

            if (realEstatePostDTO.getType().equals(EType.PLOT)) {
                Plot plotEntity = modelMapper.map(request.getPlot(), Plot.class);
                plotEntity.setRealEstatePost(realEstatePost);
                plotService.create(plotEntity);
//                Plot plot = plotService.create(plotEntity);
//                PlotDTO plotDTO = modelMapper.map(plot, PlotDTO.class);
//                if (plotDTO == null) {
//                    service.deleteById(realEstatePostDTO.getId());
//                    return ResponseEntity.ok(new BaseResponse(null, "Lưu bài viết không thành công.", HttpStatus.NO_CONTENT));
//                }
            } else if (realEstatePostDTO.getType().equals(EType.APARTMENT)) {
                Apartment apartmentEntity = modelMapper.map(request.getApartment(), Apartment.class);
                apartmentEntity.setRealEstatePost(realEstatePost);
                apartmentService.create(apartmentEntity);
//                Apartment apartment = apartmentService.create(apartmentEntity);
//                ApartmentDTO apartmentDTO = modelMapper.map(apartment, ApartmentDTO.class);
//                if (apartmentDTO == null) {
//                    service.deleteById(realEstatePostDTO.getId());
//                    return ResponseEntity.ok(new BaseResponse(null, "Lưu bài viết không thành công.", HttpStatus.NO_CONTENT));
//                }
            } else if (realEstatePostDTO.getType().equals(EType.HOUSE)) {
                House houseEntity = modelMapper.map(request.getHouse(), House.class);
                houseEntity.setRealEstatePost(realEstatePost);
                houseService.create(houseEntity);
//                House house = houseService.create(houseEntity);
//                HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
//                if (houseDTO == null) {
//                    service.deleteById(realEstatePostDTO.getId());
//                    return ResponseEntity.ok(new BaseResponse(null, "Lưu bài viết không thành công.", HttpStatus.NO_CONTENT));
//                }
            }
            List<PostMediaDTO> postMediaDTOS = request.getImages();
//            if (postMediaDTOS.isEmpty()) {
//                if (realEstatePostDTO.getType().equals(EType.HOUSE)) {
//                    houseService.deleteByRealEstatePostId(realEstatePostDTO.getId());
//                } else if (realEstatePostDTO.getType().equals(EType.PLOT)) {
//                    plotService.deleteByRealEstatePostId(realEstatePostDTO.getId());
//                } else {
//                    apartmentService.deleteByRealEstatePostId(realEstatePostDTO.getId());
//                }
//                service.deleteById(realEstatePostDTO.getId());
//                return ResponseEntity.ok(new BaseResponse(null,
//                        "Không có ảnh nào được tải lên.",
//                        HttpStatus.NO_CONTENT));
//            }
            for (PostMediaDTO postMediaDTO: postMediaDTOS) {
                postMediaService.save(modelMapper.map(postMediaDTO, PostMedia.class));
            }
            if (user.getRoles().contains(role)) {
                int pay = Util.calculatePostPrice(realEstatePostDTO.getPriority(), realEstatePostDTO.getPeriod(), realEstatePostDTO.isSell());
                PostPay postPay = new PostPay();
                postPay.setId(0L);
                postPay.setUser(user);
                postPay.setRealEstatePost(realEstatePost);
                postPay.setPrice(pay);
                postPay.setCreateAt(Instant.now());
                this.postPayService.createPostPay(postPay);

                user.setAccountBalance(user.getAccountBalance() - pay);
                user.setUpdateAt(Instant.now());
                user.setUpdateBy(user.getId());
                userService.updateUserInfo(user);
            }
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

    @PutMapping("/api/v1/real-estate-post")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> updatePost(@RequestBody RealEstatePostRequest request) {
        try {
            RealEstatePostDTO realEstatePostDTO = request.getRealEstatePost();
            realEstatePostDTO.setUpdateAt(Instant.now());
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

    @PostMapping("/api/v1/real-estate-post/plot")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> createPlot(@RequestBody PlotDTO post) {
        try {
            RealEstatePostDTO realEstatePostDTO = post.getRealEstatePost();
            PlotDTO plotDTO = null;
            Plot plotEntity = modelMapper.map(post, Plot.class);
            if (realEstatePostDTO.getType().equals(EType.PLOT)) {
                realEstatePostDTO.setCreateAt(Instant.now());
                RealEstatePost realEstatePost = modelMapper.map(realEstatePostDTO, RealEstatePost.class);
                service.create(realEstatePost);
                Plot plot = plotService.create(plotEntity);
                plotDTO = modelMapper.map(plot, PlotDTO.class);
                if (plotDTO == null) {
                    service.deleteById(realEstatePostDTO.getId());
                    return ResponseEntity.ok(new BaseResponse(null, "Lưu bài viết không thành công.", HttpStatus.NO_CONTENT));
                }
                return ResponseEntity.ok(new
                        BaseResponse(plotDTO,
                        "Đã tạo bài viết thành công. Chờ quản trị viên kiểm duyệt",
                        HttpStatus.OK));
            } else {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Loại bài đăng không hợp lệ.",
                        HttpStatus.BAD_REQUEST));
            }
        } catch (Exception e) {
            service.deleteById(post.getRealEstatePost().getId());
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi tạo bài đăng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/api/v1/real-estate-post/apartment")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> createApartment(@RequestBody ApartmentDTO post) {
        try {
            RealEstatePostDTO realEstatePostDTO = post.getRealEstatePost();
            ApartmentDTO apartmentDTO = null;
            Apartment apartmentEntity = modelMapper.map(post, Apartment.class);
            if (realEstatePostDTO.getType().equals(EType.APARTMENT)) {
                realEstatePostDTO.setCreateAt(Instant.now());
                RealEstatePost realEstatePost = modelMapper.map(realEstatePostDTO, RealEstatePost.class);
                service.create(realEstatePost);
                Apartment apartment = apartmentService.create(apartmentEntity);
                apartmentDTO = modelMapper.map(apartment, ApartmentDTO.class);
                if (apartmentDTO == null) {
                    service.deleteById(realEstatePostDTO.getId());
                    return ResponseEntity.ok(new BaseResponse(null, "Lưu bài viết không thành công.", HttpStatus.NO_CONTENT));
                }
                return ResponseEntity.ok(new
                        BaseResponse(apartmentDTO,
                        "Đã tạo bài viết thành công. Chờ quản trị viên kiểm duyệt",
                        HttpStatus.OK));
            } else {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Loại bài đăng không hợp lệ.",
                        HttpStatus.BAD_REQUEST));
            }
        } catch (Exception e) {
            service.deleteById(post.getRealEstatePost().getId());
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi tạo bài đăng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/api/v1/real-estate-post/house")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> createHouse(@RequestBody HouseDTO post) {
        try {
            RealEstatePostDTO realEstatePostDTO = post.getRealEstatePost();
            House houseEntity = modelMapper.map(post, House.class);
            HouseDTO houseDTO = null;

            if (realEstatePostDTO.getType().equals(EType.HOUSE)) {
                realEstatePostDTO.setCreateAt(Instant.now());
                RealEstatePost realEstatePost = modelMapper.map(realEstatePostDTO, RealEstatePost.class);
                service.create(realEstatePost);
                House house = houseService.create(houseEntity);
                houseDTO = modelMapper.map(house, HouseDTO.class);
                if (houseDTO == null) {
                    service.deleteById(realEstatePostDTO.getId());
                    return ResponseEntity.ok(new BaseResponse(null, "Lưu bài viết không thành công.", HttpStatus.NO_CONTENT));
                }
                return ResponseEntity.ok(new
                        BaseResponse(houseDTO,
                        "Đã tạo bài viết thành công. Chờ quản trị viên kiểm duyệt",
                        HttpStatus.OK));
            } else {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Loại bài đăng không hợp lệ.",
                        HttpStatus.BAD_REQUEST));
            }
        } catch (Exception e) {
            service.deleteById(post.getRealEstatePost().getId());
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi tạo bài đăng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/api/v1/real-estate-post/photos")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> uploadImage(@RequestBody ListImageUpload imageUpload) {
        try {
            List<PostMediaDTO> postMediaDTOS = imageUpload.getImages();
            if (postMediaDTOS.isEmpty()) {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Không có ảnh nào được tải lên.",
                        HttpStatus.NO_CONTENT));
            }
            for (PostMediaDTO postMediaDTO: postMediaDTOS) {
                postMediaService.save(modelMapper.map(postMediaDTO, PostMedia.class));
            }
            return ResponseEntity.ok(new BaseResponse(null,
                    "Lưu ảnh thành công.",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lưu ảnh. " + e.getMessage(),
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
}

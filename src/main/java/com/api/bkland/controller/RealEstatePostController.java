package com.api.bkland.controller;

import com.api.bkland.constant.enumeric.EType;
import com.api.bkland.entity.Apartment;
import com.api.bkland.entity.House;
import com.api.bkland.entity.Plot;
import com.api.bkland.entity.RealEstatePost;
import com.api.bkland.payload.dto.post.*;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.ApartmentService;
import com.api.bkland.service.HouseService;
import com.api.bkland.service.PlotService;
import com.api.bkland.service.RealEstatePostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

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
    private ObjectMapper objectMapper;

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
            return ResponseEntity.ok(new BaseResponse(basePost, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy thông tin bài đăng " + e.getMessage(),
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
}

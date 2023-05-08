package com.api.bkland.controller;

import com.api.bkland.constant.enumeric.EType;
import com.api.bkland.entity.Apartment;
import com.api.bkland.entity.House;
import com.api.bkland.entity.Plot;
import com.api.bkland.payload.dto.post.ApartmentDTO;
import com.api.bkland.payload.dto.post.BasePost;
import com.api.bkland.payload.dto.post.HouseDTO;
import com.api.bkland.payload.dto.post.PlotDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.ApartmentService;
import com.api.bkland.service.HouseService;
import com.api.bkland.service.PlotService;
import com.api.bkland.service.RealEstatePostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}

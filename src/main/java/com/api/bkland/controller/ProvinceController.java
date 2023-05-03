package com.api.bkland.controller;

import com.api.bkland.entity.Province;
import com.api.bkland.payload.dto.ProvinceDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.ProvinceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/no-auth/province")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProvinceController {
    @Autowired
    private ProvinceService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAll() {
        try {
            List<Province> provinces = service.getAll();
            if (provinces.isEmpty()) {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Không tìm thấy thông tin phù hợp",
                        HttpStatus.NO_CONTENT));
            } else {
                return ResponseEntity.ok(new BaseResponse(
                        provinces.stream().map(this::convertToDTO).collect(Collectors.toList()),
                        "",
                        HttpStatus.OK));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi thực thi. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private ProvinceDTO convertToDTO(Province province) {
        return modelMapper.map(province, ProvinceDTO.class);
    }
}

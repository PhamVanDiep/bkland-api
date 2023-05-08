package com.api.bkland.controller;

import com.api.bkland.entity.About;
import com.api.bkland.payload.dto.AboutDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.AboutService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AboutController {
    @Autowired
    private AboutService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/api/no-auth/about")
    public ResponseEntity<BaseResponse> getAbout() {
        try {
            About about = service.get();
            return ResponseEntity.ok(new BaseResponse(convertToDTO(about), "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy thông tin doanh nghiệp " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private AboutDTO convertToDTO(About about) {
        return modelMapper.map(about, AboutDTO.class);
    }
}

package com.api.bkland.controller;

import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.RealEstatePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RealEstatePostController {
    @Autowired
    private RealEstatePostService service;

    @GetMapping("/api/no-auth/real-estate-post/${id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(new BaseResponse(service.findById(id), "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy thông tin bài đăng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}

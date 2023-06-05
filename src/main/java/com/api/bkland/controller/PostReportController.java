package com.api.bkland.controller;

import com.api.bkland.config.annotation.CurrentUser;
import com.api.bkland.entity.PostReport;
import com.api.bkland.payload.PostReportDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.security.services.UserDetailsImpl;
import com.api.bkland.service.PostReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/post-report")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostReportController {
    @Autowired
    private PostReportService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody PostReportDTO body, @CurrentUser UserDetailsImpl userDetails) {
        try {
            body.setCreateBy(userDetails.getId());
            body.setCreateAt(Instant.now());
            service.save(modelMapper.map(body, PostReport.class));
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã gửi báo cáo bài viết tới quản trị viên.",
                    HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi báo cáo bài viết. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }
}

package com.api.bkland.controller;

import com.api.bkland.config.annotation.CurrentUser;
import com.api.bkland.entity.PostReport;
import com.api.bkland.payload.dto.PostReportDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.security.services.UserDetailsImpl;
import com.api.bkland.service.PostReportService;
import com.api.bkland.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/post-report")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostReportController {
    @Autowired
    private PostReportService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> create(@RequestBody PostReportDTO body, @CurrentUser UserDetailsImpl userDetails) {
        try {
            body.setCreateBy(userDetails.getId());
            body.setCreateAt(Util.getCurrentDateTime());
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

    @GetMapping("/statistic")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> getAllStatistic() {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    service.getAllStatistic(), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy thông tin thống kê báo cáo bài viết.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/post/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> findByPostId(@PathVariable("postId") String postId) {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    service.findByPostId(postId)
                            .stream()
                            .map(e -> modelMapper.map(e, PostReportDTO.class))
                            .collect(Collectors.toList()), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách báo cáo bài viết.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}

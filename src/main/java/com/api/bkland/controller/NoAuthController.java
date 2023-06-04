package com.api.bkland.controller;

import com.api.bkland.payload.dto.ReportTypeDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.ReportTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/no-auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NoAuthController {
    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/report-type")
    public ResponseEntity<BaseResponse> getAllReportType() {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    reportTypeService
                            .getAll()
                            .stream()
                            .map(e -> modelMapper.map(e, ReportTypeDTO.class))
                            .collect(Collectors.toList()), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách danh mục báo cáo.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}

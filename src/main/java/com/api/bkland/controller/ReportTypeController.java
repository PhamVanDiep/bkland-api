package com.api.bkland.controller;

import com.api.bkland.config.annotation.CurrentUser;
import com.api.bkland.entity.ReportType;
import com.api.bkland.payload.dto.ReportTypeDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.security.services.UserDetailsImpl;
import com.api.bkland.service.ReportTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/report-type")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReportTypeController {
    @Autowired
    private ReportTypeService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> create(
            @RequestBody ReportTypeDTO reportTypeDTO,
            @CurrentUser UserDetailsImpl userDetails
    ) {
        try {
            reportTypeDTO.setCreateBy(userDetails.getId());
            reportTypeDTO.setCreateAt(Instant.now());
            ReportType reportType = service.save(modelMapper.map(reportTypeDTO, ReportType.class));
            return ResponseEntity.ok(new BaseResponse(
                    modelMapper.map(reportType, ReportTypeDTO.class),
                    "Tạo danh mục báo cáo thành công.",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi tạo danh mục báo cáo.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> update(
            @RequestBody ReportTypeDTO reportTypeDTO,
            @CurrentUser UserDetailsImpl userDetails
    ) {
        try {
            reportTypeDTO.setUpdateBy(userDetails.getId());
            reportTypeDTO.setUpdateAt(Instant.now());
            ReportType reportType = service.save(modelMapper.map(reportTypeDTO, ReportType.class));
            return ResponseEntity.ok(new BaseResponse(
                    modelMapper.map(reportType, ReportTypeDTO.class),
                    "Cập nhật danh mục báo cáo thành công.",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi cập nhật danh mục báo cáo.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> delete(@PathVariable Integer id) {
        try {
            service.deletePostReportTypeByReportTypeId(id);
            service.deletePostReportType(id);
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Xóa danh mục báo cáo thành công.",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi xóa danh mục báo cáo.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}

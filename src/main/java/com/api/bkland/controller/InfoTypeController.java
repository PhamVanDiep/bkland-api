package com.api.bkland.controller;

import com.api.bkland.entity.InfoType;
import com.api.bkland.payload.dto.InfoTypeDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.InfoTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/info-type")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InfoTypeController {
    @Autowired
    private InfoTypeService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/skip")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> getAllSkip() {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    service.getAllSkip6().stream().map(e -> convertToDTO(e)).collect(Collectors.toList()),
                    "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách danh mục tin tức. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> getAll() {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    service.getAll().stream().map(e -> convertToDTO(e)).collect(Collectors.toList()),
                    "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách danh mục tin tức. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> findById(@PathVariable("id") Integer id) {
        try {
            InfoType infoType = service.findById(id);
            if (infoType == null) {
                return ResponseEntity.ok(new BaseResponse(
                        null,
                        "Không tìm thấy thông tin danh mục.",
                        HttpStatus.NO_CONTENT
                ));
            }
            return ResponseEntity.ok(new BaseResponse(
                    convertToDTO(infoType), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy thông tin danh mục. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> insert(@RequestBody InfoTypeDTO infoTypeDTO) {
        try {
            infoTypeDTO.setCreateBy("admin");
            infoTypeDTO.setCreateAt(Instant.now());
            InfoType infoType = service.createInfoType(convertToEntity(infoTypeDTO));
            return ResponseEntity.ok(new BaseResponse(convertToDTO(infoType),
                    "Tạo danh mục tin tức mới thành công.",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi tạo danh mục tin tức. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> update(@RequestBody InfoTypeDTO infoTypeDTO) {
        try {
            infoTypeDTO.setUpdateBy("admin");
            infoTypeDTO.setUpdateAt(Instant.now());
            InfoType infoType = service.updateInfoType(convertToEntity(infoTypeDTO));
            return ResponseEntity.ok(new BaseResponse(convertToDTO(infoType),
                    "Cập nhật danh mục tin tức thành công.",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi cập nhật danh mục tin tức. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") String id) {
        try {
            Integer infoTypeId = Integer.valueOf(id);
            service.deleteInfoPostByInfoTypeId(infoTypeId);
            service.deleteInfoType(infoTypeId);
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Xóa danh mục tin tức thành công.",
                    HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi xóa danh mục tin tức. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    private InfoType convertToEntity(InfoTypeDTO infoTypeDTO) {
        return modelMapper.map(infoTypeDTO, InfoType.class);
    }

    private InfoTypeDTO convertToDTO(InfoType infoType) {
        return modelMapper.map(infoType, InfoTypeDTO.class);
    }
}

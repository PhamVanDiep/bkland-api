package com.api.bkland.controller;

import com.api.bkland.entity.UserDeviceToken;
import com.api.bkland.payload.dto.UserDeviceTokenDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.UserDeviceTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/user-device-token")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserDeviceTokenController {
    @Autowired
    private UserDeviceTokenService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> insertUserDeviceToken(@RequestBody UserDeviceTokenDTO body) {
        try {
            body.setCreateAt(Instant.now());
            service.create(convertToEntity(body));
            return ResponseEntity.ok(new BaseResponse(null, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi thêm thông tin người dùng nhận thông báo. "
                            + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private UserDeviceToken convertToEntity(UserDeviceTokenDTO dto) {
        return modelMapper.map(dto, UserDeviceToken.class);
    }
}

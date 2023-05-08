package com.api.bkland.controller;

import com.api.bkland.entity.User;
import com.api.bkland.payload.dto.UserDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> getUserById(@PathVariable("userId") String userId) {
        try {
            User user = service.findById(userId);
            if (user == null) {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Không tìm thấy thông tin người dùng.",
                        HttpStatus.NO_CONTENT));
            } else {
                return ResponseEntity.ok(new BaseResponse(convertToDTO(user),
                        "", HttpStatus.OK));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy thông tin người dùng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}

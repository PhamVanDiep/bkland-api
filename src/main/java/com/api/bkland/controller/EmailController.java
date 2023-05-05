package com.api.bkland.controller;

import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.EmailService;
import com.api.bkland.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/no-auth/send-email")
public class EmailController {
    @Autowired
    private EmailService service;

    @PostMapping("/{email}")
    public ResponseEntity<BaseResponse> sendEmail(@PathVariable("email") String email) {
        try {
            String code = Util.getRandomNumberString();
            service.sendEmail(email,
                    "Mã xác thực dùng để đổi mật khẩu trên ứng dụng bkland",
                    "Mã xác thực của bạn là: " + code);
            return ResponseEntity.ok(new BaseResponse(code, "Đã gửi mã xác thực thành công.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi gửi email " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}

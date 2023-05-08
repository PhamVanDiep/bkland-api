package com.api.bkland.controller;

import javax.validation.Valid;

import com.api.bkland.entity.UserDeviceToken;
import com.api.bkland.payload.dto.UserDTO;
import com.api.bkland.payload.dto.UserDeviceTokenDTO;
import com.api.bkland.payload.request.ForgotPassword;
import com.api.bkland.payload.request.LoginRequest;
import com.api.bkland.payload.request.SignupRequest;
import com.api.bkland.payload.request.TokenRefreshRequest;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.AuthService;
import com.api.bkland.service.UserDeviceTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Api(value = "Authorization Rest API",
        description = "Defines endpoints that can be hit only when the user is not logged in. It's not secured by default.")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    private UserDeviceTokenService userDeviceTokenService;

    @ApiOperation("login")
    @PostMapping("/signin")
    public ResponseEntity<BaseResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @ApiOperation("register")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> registerUser(@Valid @RequestBody UserDTO signUpRequest) {
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }

    @ApiOperation("refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(authService.refreshToken(tokenRefreshRequest));
    }

    @ApiOperation("check email exist")
    @GetMapping("/email-exist/{email}")
    public ResponseEntity<BaseResponse> emailExist(@PathVariable("email") String email) {
        return ResponseEntity.ok(authService.emailExist(email));
    }

    @ApiOperation("logout")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse> logout(@RequestBody UserDeviceTokenDTO userDeviceTokenDTO) {
        try {
            UserDeviceToken userDeviceToken = userDeviceTokenService
                    .findByUserIdAndDeviceInfo(userDeviceTokenDTO.getUserId(), userDeviceTokenDTO.getDeviceInfo());
            if (userDeviceToken == null) {
                return ResponseEntity.ok(new BaseResponse(null, "", HttpStatus.OK));
            }
            userDeviceToken.setLogout(true);
            userDeviceToken.setUpdateBy(userDeviceTokenDTO.getUpdateBy());
            userDeviceToken.setUpdateAt(Instant.now());
            userDeviceTokenService.update(userDeviceToken);
            return ResponseEntity.ok(new BaseResponse(null, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null, "Đã xảy ra lỗi khi đăng xuất",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @ApiOperation("forgot password - change password")
    @PostMapping("/change-password")
    public ResponseEntity<BaseResponse> changePassword(@RequestBody ForgotPassword forgotPassword) {
        try {
            if (authService.changePassword(forgotPassword)) {
                return ResponseEntity.ok(new BaseResponse(null, "Đổi mật khẩu thành công",
                        HttpStatus.OK));
            } else {
                return ResponseEntity.ok(new BaseResponse(null, "Đã xảy ra lỗi khi đổi mật khẩu",
                        HttpStatus.INTERNAL_SERVER_ERROR));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi đổi mật khẩu " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}

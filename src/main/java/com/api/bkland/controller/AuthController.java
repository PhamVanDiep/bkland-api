package com.api.bkland.controller;

import javax.validation.Valid;

import com.api.bkland.payload.dto.UserDTO;
import com.api.bkland.payload.request.LoginRequest;
import com.api.bkland.payload.request.SignupRequest;
import com.api.bkland.payload.request.TokenRefreshRequest;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Api(value = "Authorization Rest API",
        description = "Defines endpoints that can be hit only when the user is not logged in. It's not secured by default.")
public class AuthController {
    @Autowired
    AuthService authService;

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
}

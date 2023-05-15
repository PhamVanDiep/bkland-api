package com.api.bkland.controller;

import com.api.bkland.entity.User;
import com.api.bkland.payload.dto.*;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.payload.response.UserInfoResponse;
import com.api.bkland.service.DistrictService;
import com.api.bkland.service.ProvinceService;
import com.api.bkland.service.UserService;
import com.api.bkland.service.WardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private WardService wardService;

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

    @GetMapping("/info/{userId}")
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> getUserInfoById(@PathVariable("userId") String userId) {
        try {
            User user = service.findById(userId);
            if (user == null) {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Không tìm thấy thông tin người dùng.",
                        HttpStatus.NO_CONTENT));
            } else {
                DistrictDTO districtDTO = modelMapper.map(districtService.findByCode(user.getDistrictCode()), DistrictDTO.class);
                ProvinceDTO provinceDTO = modelMapper.map(provinceService.findByCode(user.getProvinceCode()), ProvinceDTO.class);
                WardDTO wardDTO = modelMapper.map(wardService.findByCode(user.getWardCode()), WardDTO.class);

                UserInfoResponse userInfoResponse = new UserInfoResponse();
                userInfoResponse.setAddress(user.getAddress());
                userInfoResponse.setAccountBalance(user.getAccountBalance());
                userInfoResponse.setAvatarUrl(user.getAvatarUrl());
                userInfoResponse.setEmail(user.getEmail());
                userInfoResponse.setCreateAt(user.getCreateAt());
                userInfoResponse.setDistrict(districtDTO);
                userInfoResponse.setProvince(provinceDTO);
                userInfoResponse.setWard(wardDTO);
                userInfoResponse.setEnable(user.isEnable());
                userInfoResponse.setGender(user.getGender());
                userInfoResponse.setId(user.getId());
                userInfoResponse.setCreateBy(user.getCreateBy());
                userInfoResponse.setIdentification(user.getIdentification());
                userInfoResponse.setEmail(user.getEmail());
                userInfoResponse.setDateOfBirth(user.getDateOfBirth());
                userInfoResponse.setFirstName(user.getFirstName());
                userInfoResponse.setMiddleName(user.getMiddleName());
                userInfoResponse.setLastName(user.getLastName());
                userInfoResponse.setUpdateAt(user.getUpdateAt());
                userInfoResponse.setUpdateBy(userInfoResponse.getUpdateBy());
                userInfoResponse.setRoles(user.getRoles().stream().map(e -> modelMapper.map(e, RoleDTO.class)).collect(Collectors.toSet()));
                userInfoResponse.setPassword(user.getPassword());
                userInfoResponse.setPhoneNumber(user.getPhoneNumber());
                userInfoResponse.setUsername(user.getUsername());
                return ResponseEntity.ok(new BaseResponse(userInfoResponse,
                        "", HttpStatus.OK));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy thông tin người dùng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> updateUserInfo(@RequestBody UserDTO userDTO) {
        try {
            service.updateUserInfo(modelMapper.map(userDTO, User.class));
            return ResponseEntity.ok(new BaseResponse(null, "Cập nhật thông tin người dùng thành công.", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi cập nhật thông tin người dùng " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}

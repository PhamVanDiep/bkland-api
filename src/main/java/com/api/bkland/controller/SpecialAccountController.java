package com.api.bkland.controller;

import com.api.bkland.entity.SpecialAccount;
import com.api.bkland.payload.dto.SpecialAccountDTO;
import com.api.bkland.payload.request.AgencyRegisterRequest;
import com.api.bkland.payload.response.AgencyInfoResponse;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.SpecialAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class SpecialAccountController {
    @Autowired
    private SpecialAccountService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/api/no-auth/special-account")
    public ResponseEntity<BaseResponse> createSpecialAccount(@RequestBody SpecialAccountDTO specialAccountDTO) {
        try {
            SpecialAccount serviceResponse = service.addSpecialAccount(convertToEntity(specialAccountDTO));
            return ResponseEntity.ok(new BaseResponse(convertToDTO(serviceResponse),
                    "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi thực thi. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/api/v1/special-account/agency/{userId}")
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> getAgencyInfo(@PathVariable("userId") String userId) {
        try {
            AgencyInfoResponse agencyInfoResponse = service.findAgencyInfo(userId);
            if (agencyInfoResponse == null) {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Không tìm thấy thông tin đăng ký môi giới của tài khoản này.",
                        HttpStatus.NO_CONTENT));
            }
            return ResponseEntity.ok(new BaseResponse(agencyInfoResponse, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi lấy thông tin đăng ký tài khoản môi giới. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/api/v1/special-account/agency")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<BaseResponse> createAgency(@RequestBody AgencyRegisterRequest request) {
        if (service.isAgency(request.getUserId())) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Tài khoản đã được đăng ký là môi giới trước đó.",
                    HttpStatus.NOT_ACCEPTABLE));
        }
        return ResponseEntity.ok(service.agencyRegister(request));
    }

    @PutMapping("/api/v1/special-account/agency")
    @PreAuthorize("hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> updateAgency(@RequestBody AgencyRegisterRequest request) {
        return ResponseEntity.ok(service.agencyUpdate(request));
    }

    @DeleteMapping("/api/v1/special-account/agency/{userId}")
    @PreAuthorize("hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> deleteAgency(@PathVariable("userId") String userId) {
//        return ResponseEntity.ok(service.deleteAgency(userId));
        try {
            service.userRoleDeleteByUserId(userId);
            service.agencyDistrictDeleteByUserId(userId);
            service.deleteByUserId(userId);
            return ResponseEntity.ok(new BaseResponse(null,
                    "Hủy đăng ký tài khoản môi giới thành công.",
                    HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null,
                    "Đã xảy ra lỗi khi hủy đăng ký tài khoản môi giới. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private SpecialAccount convertToEntity(SpecialAccountDTO specialAccountDTO) {
        return modelMapper.map(specialAccountDTO, SpecialAccount.class);
    }

    private SpecialAccountDTO convertToDTO(SpecialAccount specialAccount) {
        return modelMapper.map(specialAccount, SpecialAccountDTO.class);
    }
}

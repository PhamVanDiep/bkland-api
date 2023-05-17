package com.api.bkland.controller;

import com.api.bkland.entity.SpecialAccount;
import com.api.bkland.payload.dto.SpecialAccountDTO;
import com.api.bkland.payload.request.AgencyRegisterRequest;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.SpecialAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/api/v1/special-account/agency")
    public ResponseEntity<BaseResponse> createAgency(@RequestBody AgencyRegisterRequest request) {
        return ResponseEntity.ok(service.agencyRegister(request));
    }

    @PutMapping("/api/v1/special-account/agency")
    public ResponseEntity<BaseResponse> updateAgency(@RequestBody AgencyRegisterRequest request) {
        return ResponseEntity.ok(service.agencyUpdate(request));
    }

    private SpecialAccount convertToEntity(SpecialAccountDTO specialAccountDTO) {
        return modelMapper.map(specialAccountDTO, SpecialAccount.class);
    }

    private SpecialAccountDTO convertToDTO(SpecialAccount specialAccount) {
        return modelMapper.map(specialAccount, SpecialAccountDTO.class);
    }
}

package com.api.bkland.controller;

import com.api.bkland.entity.AdministrativeUnit;
import com.api.bkland.entity.Ward;
import com.api.bkland.payload.dto.AdministrativeUnitDTO;
import com.api.bkland.payload.dto.WardDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.WardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/no-auth/ward")
public class WardController {
    @Autowired
    private WardService wardService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getByCode(@PathVariable(name = "id") String id) {
        try {
            Ward serviceResponse = wardService.findByCode(id);
            if (serviceResponse == null) {
                return ResponseEntity.ok(new BaseResponse(null, "Khong tim thay ban ghi nao", HttpStatus.NOT_FOUND));
            } else {
                return ResponseEntity.ok(new BaseResponse(convertToDto(serviceResponse), "", HttpStatus.OK));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null, "Da xay ra loi khi thuc hien", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private WardDTO convertToDto(Ward ward) {
        return modelMapper.map(ward, WardDTO.class);
    }
}

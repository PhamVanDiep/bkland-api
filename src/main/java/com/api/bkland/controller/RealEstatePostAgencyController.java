package com.api.bkland.controller;

import com.api.bkland.config.annotation.CurrentUser;
import com.api.bkland.constant.enumeric.ERepAgencyStatus;
import com.api.bkland.entity.RealEstatePost;
import com.api.bkland.entity.RealEstatePostAgency;
import com.api.bkland.payload.dto.RealEstatePostAgencyDTO;
import com.api.bkland.payload.dto.post.RealEstatePostDTO;
import com.api.bkland.payload.request.RealEstatePostAgencyRequest;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.payload.response.REPAgencyResponse;
import com.api.bkland.security.services.UserDetailsImpl;
import com.api.bkland.service.NotifyService;
import com.api.bkland.service.RealEstatePostAgencyService;
import com.api.bkland.service.RealEstatePostService;
import com.api.bkland.service.SpecialAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/real-estate-post-agency")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RealEstatePostAgencyController {
    @Autowired
    private RealEstatePostAgencyService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpecialAccountService specialAccountService;

    @Autowired
    private RealEstatePostService realEstatePostService;

    @Autowired
    private NotifyService notifyService;

    @GetMapping("/{agencyId}")
    @PreAuthorize("hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> findByAgencyId(@PathVariable String agencyId) {
        try {
            if (!specialAccountService.isAgency(agencyId)) {
                return ResponseEntity.ok(new BaseResponse(
                        null, "Người dùng này không phải là môi giới.", HttpStatus.NOT_ACCEPTABLE));
            }
            List<RealEstatePostAgency> realEstatePostAgencies = service.findByAgencyId(agencyId);
            List<REPAgencyResponse> repAgencyResponses = realEstatePostAgencies.stream()
                    .map(e -> modelMapper.map(e, REPAgencyResponse.class))
                    .collect(Collectors.toList());
            repAgencyResponses.stream().forEach(e -> {
                RealEstatePost realEstatePost = realEstatePostService.findByIdAndEnable(e.getRealEstatePostId());
                if (realEstatePost != null) {
                    e.setRealEstatePostDTO(modelMapper.map(realEstatePost, RealEstatePostDTO.class));
                }
            });
            return ResponseEntity.ok(new BaseResponse(repAgencyResponses, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng nhờ môi giới giúp đỡ.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/requested/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<BaseResponse> findByCreateBy(@PathVariable String userId) {
        try {
            List<RealEstatePostAgency> realEstatePostAgencies = service.findByCreateBy(userId);
            List<REPAgencyResponse> repAgencyResponses = realEstatePostAgencies.stream()
                    .map(e -> modelMapper.map(e, REPAgencyResponse.class))
                    .collect(Collectors.toList());
            repAgencyResponses.stream().forEach(e -> {
                RealEstatePost realEstatePost = realEstatePostService.findByIdAndEnable(e.getRealEstatePostId());
                if (realEstatePost != null) {
                    e.setRealEstatePostDTO(modelMapper.map(realEstatePost, RealEstatePostDTO.class));
                }
            });
            return ResponseEntity.ok(new BaseResponse(repAgencyResponses, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng đã nhờ môi giới giúp đỡ.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<BaseResponse> create(@RequestBody RealEstatePostAgencyRequest body, @CurrentUser UserDetailsImpl userDetails) {
        try {
            if (!realEstatePostService.existsByIdAndEnable(body.getRealEstatePostId())) {
                return ResponseEntity.ok(new BaseResponse(null, "Bài viết không tồn tại", HttpStatus.NO_CONTENT));
            }
            List<RealEstatePostAgency> realEstatePostAgencies = new ArrayList<>();
            for (String e: body.getAgencies()) {
                if (!specialAccountService.isAgency(e)) {
                    return ResponseEntity.ok(new BaseResponse(null, "Người được gửi yêu cầu không phải môi giới.", HttpStatus.NO_CONTENT));
                } else {
                    if (service.inArea(body.getRealEstatePostId(), e)) {
                        return ResponseEntity.ok(new BaseResponse(null,
                                "Bài viết không nằm trong khu vực đăng ký của môi giới.", HttpStatus.NOT_ACCEPTABLE));
                    }
                }
                RealEstatePostAgency realEstatePostAgency = new RealEstatePostAgency();
                realEstatePostAgency.setCreateBy(userDetails.getId());
                realEstatePostAgency.setCreateAt(Instant.now());
                realEstatePostAgency.setStatus(ERepAgencyStatus.DA_GUI_YEU_CAU);
                realEstatePostAgency.setId(0L);
                realEstatePostAgency.setAgencyId(e);
                realEstatePostAgency.setRealEstatePostId(body.getRealEstatePostId());
                realEstatePostAgencies.add(realEstatePostAgency);
            }
            service.saveAll(realEstatePostAgencies);
            body.getAgencies().stream().forEach(e -> {
                notifyService.thongBaoCoBaiDangNhoGiup(e);
            });
            return ResponseEntity.ok(new BaseResponse(null, "Đã gửi yêu cầu nhờ giúp đỡ.", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi gửi yêu cầu giúp đỡ",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> update(@RequestBody RealEstatePostAgencyDTO body, @CurrentUser UserDetailsImpl userDetails) {
        try {
            Optional<RealEstatePostAgency> realEstatePostAgencyOptional = service.findById(body.getId());
            if (realEstatePostAgencyOptional.isEmpty()) {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Không tìm thấy lời yêu cầu này.",
                        HttpStatus.NO_CONTENT));
            }
            RealEstatePostAgency realEstatePostAgency = realEstatePostAgencyOptional.get();
            if (!userDetails.getId().equals(realEstatePostAgency.getAgencyId())) {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Bạn không có quyền cập nhật trạng thái lời yêu cầu này.",
                        HttpStatus.NOT_ACCEPTABLE));
            }
            realEstatePostAgency.setStatus(body.getStatus());
            realEstatePostAgency.setUpdateBy(userDetails.getId());
            realEstatePostAgency.setUpdateAt(Instant.now());
            Long response = service.updateStatus(realEstatePostAgency);
            notifyService.thongBaoCapNhatTrangThaiBaiDang(realEstatePostAgency.getRealEstatePostId(), body.getStatus());
            return ResponseEntity.ok(new BaseResponse(response, "Cập nhật trạng thái lời yêu cầu thành công.", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi cập nhật yêu cầu giúp đỡ",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id, @CurrentUser UserDetailsImpl userDetails) {
        try {
            Optional<RealEstatePostAgency> realEstatePostAgencyOptional = service.findById(id);
            if (realEstatePostAgencyOptional.isEmpty()) {
                return ResponseEntity.ok(new BaseResponse(null,
                        "Không tìm thấy lời yêu cầu này.",
                        HttpStatus.NO_CONTENT));
            }
            RealEstatePostAgency realEstatePostAgency = realEstatePostAgencyOptional.get();
            if (userDetails.getId().equals(realEstatePostAgency.getCreateBy())
            && realEstatePostAgency.getStatus().equals(ERepAgencyStatus.DA_GUI_YEU_CAU)) {
                service.deleteById(id);
                return ResponseEntity.ok(new BaseResponse(null,
                        "Xóa yêu cầu nhờ môi giới thành công.",
                        HttpStatus.OK));
            }
            return ResponseEntity.ok(new BaseResponse(null,
                    "Bạn không có quyền xóa lời yêu cầu này.",
                    HttpStatus.NOT_ACCEPTABLE));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi xóa yêu cầu giúp đỡ",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}

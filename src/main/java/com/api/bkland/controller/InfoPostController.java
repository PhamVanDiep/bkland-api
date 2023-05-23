package com.api.bkland.controller;

import com.api.bkland.entity.InfoPost;
import com.api.bkland.payload.dto.InfoPostDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.InfoPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class InfoPostController {
    @Autowired
    private InfoPostService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/api/v1/info-post")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> getAll() {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    service.findAll(), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài đăng. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @PostMapping("/api/v1/info-post")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> create(@RequestBody InfoPostDTO infoPostDTO) {
        try {
            infoPostDTO.setCreateAt(Instant.now());
            InfoPost infoPost = service.create(convertToEntity(infoPostDTO));
            return ResponseEntity.ok(new BaseResponse(
                    convertToDTO(infoPost),
                    "Tạo bài viết thành công.",
                    HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi tạo bài viết. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @PutMapping("/api/v1/info-post")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> update(@RequestBody InfoPostDTO infoPostDTO) {
        try {
            infoPostDTO.setUpdateAt(Instant.now());
            InfoPost infoPost = service.update(convertToEntity(infoPostDTO));
            return ResponseEntity.ok(new BaseResponse(
                    convertToDTO(infoPost),
                    "Cập nhật bài viết thành công.",
                    HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi cập nhật bài viết. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/api/no-auth/info-post/{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable("id") Long id) {
        try {
            InfoPost infoPost = service.findById(id);
            if (infoPost == null) {
                return ResponseEntity.ok(new BaseResponse(
                        null,
                        "Không tìm thấy bài viết tương ứng.",
                        HttpStatus.NO_CONTENT
                ));
            }
            return ResponseEntity.ok(new BaseResponse(
                    convertToDTO(infoPost), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi tìm kiếm bài viết. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @DeleteMapping("/api/v1/info-post/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasROLE('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> deleteInfoPost(@PathVariable String id) {
        try {
            Long postId = Long.valueOf(id);
            service.deleteById(postId);
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Xóa bài viết thành công.",
                    HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi xóa bài viết. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/api/v1/info-post/enterprise/{id}")
    public ResponseEntity<BaseResponse> getInfoPostByUserId(@PathVariable("id") String userId) {
        try {
            List<InfoPost> infoPosts = service.findByUserId(userId);
            return ResponseEntity.ok(new BaseResponse(
                    infoPosts.stream().map(this::convertToDTO).collect(Collectors.toList()),
                    "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách dự án." + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    private InfoPostDTO convertToDTO(InfoPost infoPost) {
        return modelMapper.map(infoPost, InfoPostDTO.class);
    }

    private InfoPost convertToEntity(InfoPostDTO infoPostDTO) {
        return modelMapper.map(infoPostDTO, InfoPost.class);
    }
}

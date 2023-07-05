package com.api.bkland.controller;

import com.api.bkland.config.annotation.CurrentUser;
import com.api.bkland.constant.enumeric.ERole;
import com.api.bkland.entity.Project;
import com.api.bkland.entity.ProjectInterested;
import com.api.bkland.entity.ProjectParam;
import com.api.bkland.payload.dto.InterestedDTO;
import com.api.bkland.payload.dto.ProjectDTO;
import com.api.bkland.payload.dto.ProjectInterestedDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.security.services.UserDetailsImpl;
import com.api.bkland.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> create(@RequestBody ProjectDTO body, @CurrentUser UserDetailsImpl userDetails) {
        try {
            Project project = modelMapper.map(body, Project.class);
            String id = UUID.randomUUID().toString();
            project.setId(id);
            project.setEnable(true);
            project.setCreateBy(userDetails.getId());
            project.setCreateAt(Instant.now());
            project
                    .getProjectParams()
                        .stream()
                        .parallel()
                        .forEach(e -> {
                            if (e.getId() < 0) {
                                e.setId(0L);
                            }
                            e.getProject().setId(id);
                        });
            projectService.save(project);
            return ResponseEntity.ok(new BaseResponse(id, "Tạo dự án thành công.", HttpStatus.OK));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Tạo dự án không thành công. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> update(@RequestBody ProjectDTO body, @CurrentUser UserDetailsImpl userDetails) {
        try {
            if (!projectService.existsByIdAndEnable(body.getId())) {
                return ResponseEntity.ok(new BaseResponse(
                        null,
                        "Không tồn taị dự án nên không thể cập nhật.",
                        HttpStatus.NOT_ACCEPTABLE));
            }
            Project project = modelMapper.map(body, Project.class);
            project.setUpdateBy(userDetails.getId());
            project.setUpdateAt(Instant.now());
            for (ProjectParam e: project.getProjectParams()) {
                if (!e.getProject().getId().equals(body.getId())) {
                    return ResponseEntity.ok(new BaseResponse(
                            null,
                            "Khóa ngoại của tham số không trùng với khóa chính của project.",
                            HttpStatus.NOT_ACCEPTABLE));
                }
                if ((e.getId() > 0 && !projectService.paramExistsByIdAndProjectId(e.getId(), e.getProject().getId()))
                || e.getId() < 0) {
                    e.setId(0L);
                    e.getProject().setId(project.getId());
                }
            }
            projectService.save(project);
            return ResponseEntity.ok(new BaseResponse(project.getId(), "Cập nhật thông tin dự án thành công.", HttpStatus.OK));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Cập nhật thông tin dự án không thành công. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/param/{id}")
    @PreAuthorize("hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> deleteParam(@PathVariable Long id, @CurrentUser UserDetailsImpl userDetails) {
        try {
            if (!projectService.paramBelongToUser(userDetails.getId(), id)) {
                return ResponseEntity.ok(new BaseResponse(null, "Bạn không có quyền xóa tham số này.", HttpStatus.NOT_ACCEPTABLE));
            }
            projectService.deleteParam(id);
            return ResponseEntity.ok(new BaseResponse(null, "Xóa tham số thành công.", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi xóa tham số. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ENTERPRISE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> deleteProject(@PathVariable String id, @CurrentUser UserDetailsImpl userDetails) {
        try {
            final boolean[] canDelete = {false};
            userDetails.getAuthorities()
                    .stream()
                    .parallel()
                    .forEach(e -> {
                        if (e.getAuthority().equals(ERole.ROLE_ADMIN.toString())) {
                            canDelete[0] = true;
                        } else if (e.getAuthority().equals(ERole.ROLE_ENTERPRISE.toString())
                        && projectService.projectBelongToUser(userDetails.getId(), id)) {
                            canDelete[0] = true;
                        }
                    });
            if (canDelete[0]) {
                projectService.delete(id);
            }
            return ResponseEntity.ok(new BaseResponse(null, "Xóa dự án thành công.", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi xóa dự án. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> findAll() {
        try {
            List<Project> projects = projectService.findAll();
            List<ProjectDTO> response = projects
                    .stream()
                    .map(e -> modelMapper.map(e, ProjectDTO.class)).toList();
            return ResponseEntity.ok(new BaseResponse(response, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách dự án. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> findByUser(@CurrentUser UserDetailsImpl userDetails) {
        try {
            List<Project> projects = projectService.findByUserId(userDetails.getId());
            List<ProjectDTO> response = projects
                    .stream()
                    .map(e -> modelMapper.map(e, ProjectDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new BaseResponse(response, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách dự án của người dùng. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> findById(@PathVariable String id) {
        try {
            Project project = projectService.findById(id);
            if (project == null) {
                return ResponseEntity.ok(new BaseResponse(
                        null,
                        "Không tìm thấy thông tin bài viết. ",
                        HttpStatus.NO_CONTENT));
            }
            return ResponseEntity.ok(new BaseResponse(modelMapper.map(project, ProjectDTO.class), "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy thông tin bài viết. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/interested")
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_USER')")
    public ResponseEntity<BaseResponse> userInterested(@RequestBody ProjectInterestedDTO body, @CurrentUser UserDetailsImpl userDetails) {
        try {
            if (!projectService.existsByIdAndEnable(body.getProjectId())) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            Optional<ProjectInterested> interestedOptional = projectService.findByUserIdAndRealEstatePostId(userDetails.getId(), body.getProjectId());
            if (interestedOptional.isEmpty()) {
                body.setCreateBy(userDetails.getId());
                body.setCreateAt(Instant.now());
                body.setId(0L);
                body.setUserId(userDetails.getId());
                ProjectInterested interested = modelMapper.map(body, ProjectInterested.class);
                return ResponseEntity.ok(new BaseResponse(
                        modelMapper.map(projectService.saveInterested(interested), ProjectInterestedDTO.class),
                        "", HttpStatus.OK
                ));
            } else {
                projectService.deleteInterested(interestedOptional.get().getId());
                return ResponseEntity.ok(new BaseResponse(interestedOptional.get().getId(), "DELETED", HttpStatus.OK));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lưu thông tin quan tâm bài đăng.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/statistic")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> statistic(@RequestParam Integer id, @RequestParam Integer year) {
        try {
            return ResponseEntity.ok(new BaseResponse(projectService.getChartOption(id, year), "", HttpStatus.OK));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy dữ liệu thống kê. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/interested-of-user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_AGENCY')")
    public ResponseEntity<BaseResponse> findAllProjectsInterestedByUser(@CurrentUser UserDetailsImpl userDetails) {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    projectService.findAllProjectsInterestedByUser(userDetails.getId())
                            .stream().map(e -> modelMapper.map(e, ProjectDTO.class)).toList(), "", HttpStatus.OK));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài viết người dùng đã quan tâm. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }
}

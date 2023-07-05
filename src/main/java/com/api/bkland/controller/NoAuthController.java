package com.api.bkland.controller;

import com.api.bkland.config.annotation.CurrentUser;
import com.api.bkland.constant.enumeric.EProjectType;
import com.api.bkland.entity.*;
import com.api.bkland.payload.dto.*;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.security.services.UserDetailsImpl;
import com.api.bkland.service.ChatService;
import com.api.bkland.service.PostReportService;
import com.api.bkland.service.ProjectService;
import com.api.bkland.service.ReportTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/no-auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NoAuthController {
    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private PostReportService postReportService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/report-type/rep")
    public ResponseEntity<BaseResponse> getAllReportType() {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    reportTypeService
                            .getAllByIsForum(false)
                            .stream()
                            .map(e -> modelMapper.map(e, ReportTypeDTO.class))
                            .collect(Collectors.toList()), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách danh mục báo cáo của bài đăng bán / cho thuê.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/post-report")
    public ResponseEntity<BaseResponse> create(@RequestBody PostReportDTO body) {
        try {
            body.setCreateBy("anonymous");
            body.setCreateAt(Instant.now());
            postReportService.save(modelMapper.map(body, PostReport.class));
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã gửi báo cáo bài viết tới quản trị viên.",
                    HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi báo cáo bài viết. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/project/isInterested")
    public ResponseEntity<BaseResponse> isInterested(@RequestParam("userId") String userId,
                                                     @RequestParam("deviceInfo") String deviceInfo,
                                                     @RequestParam("projectId") String projectId) {
        try {
            return ResponseEntity.ok(new BaseResponse(
                    projectService.isInterested(userId, projectId, deviceInfo), "", HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy thông tin người dùng quan tâm của bài viết.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @PostMapping("/project/interested")
    public ResponseEntity<BaseResponse> anonymousInterested(@RequestBody ProjectInterestedDTO body) {
        try {
            if (!projectService.existsByIdAndEnable(body.getProjectId())) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy bài đăng phù hợp.", HttpStatus.NOT_FOUND));
            }
            Optional<ProjectInterested> interestedOptional = projectService.findByDeviceInfoAndProjectId(body.getDeviceInfo(), body.getProjectId());
            if (interestedOptional.isEmpty()) {
                body.setCreateBy("anonymous");
                body.setCreateAt(Instant.now());
                body.setId(0L);
                body.setUserId("anonymous");
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

    @GetMapping("/chat/chat-room")
    public ResponseEntity<BaseResponse> findAnonymousChatRoom(@RequestParam String userDeviceId) {
        try {
            ChatRoom chatRoom = chatService.findAnonymousChatRoom(userDeviceId);
            return ResponseEntity.ok(new BaseResponse(modelMapper.map(chatRoom, ChatRoomDTO.class), "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy thông tin cuộc hội thoại với quản trị viên.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/chat/chat-room/detail")
    public ResponseEntity<BaseResponse> getAnonymousChatRoomDetail(@RequestParam Integer id, @RequestParam String userDeviceId) {
        try {
            ChatRoom chatRoom = chatService.findChatRoomById(id);
            if (chatRoom.isAnonymous() && chatRoom.getFirstUserId().equals(userDeviceId)) {
                return ResponseEntity.ok(new BaseResponse(modelMapper.map(chatRoom, ChatRoomDTO.class), "", HttpStatus.OK));
            }
            return ResponseEntity.ok(new BaseResponse(null, "Bạn không có quyền lấy thông tin của cuộc hội thoại này", HttpStatus.NOT_ACCEPTABLE));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy thông tin cuộc hội thoại với quản trị viên.",
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/chat/message")
    public ResponseEntity<BaseResponse> createAnonymousMessage(@RequestBody MessageDTO messageDTO) {
        try {
            ChatRoom chatRoom = chatService.findChatRoomById(messageDTO.getChatRoomId());
            if (chatRoom == null) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy thông tin cuộc trò chuyện", HttpStatus.NO_CONTENT));
            }
            messageDTO.setCreateAt(Instant.now());
            Message message = modelMapper.map(messageDTO, Message.class);
            message.setChatRoom(chatRoom);
            message.setId(0L);
            Message response = chatService.saveMessage(message);
            return ResponseEntity.ok(new BaseResponse(response, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi gửi tin nhắn " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/project/type")
    public ResponseEntity<BaseResponse> findByType(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String type) {
        try {
            List<Project> projects = projectService.findByTypePageable(page, pageSize, EProjectType.valueOf(type));
            List<ProjectDTO> response = projects.stream().map(e -> modelMapper.map(e, ProjectDTO.class)).collect(Collectors.toList());
            return ResponseEntity.ok(new BaseResponse(response, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài viết dự án. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }

    @GetMapping("/project/increase-view/{id}")
    public ResponseEntity<BaseResponse> increaseView(@PathVariable String id) {
        try {
            if (!projectService.existsByIdAndEnable(id)) {
                return ResponseEntity.ok(new BaseResponse(null, "Không tìm thấy thông tin dự án", HttpStatus.OK));
            }
//            Project project = projectService.findById(id, true);
//            if (project == null) {
//                return ResponseEntity.ok(new BaseResponse(
//                        null,
//                        "Không tìm thấy bài viết",
//                        HttpStatus.NO_CONTENT
//                ));
//            }
//            return ResponseEntity.ok(new BaseResponse(modelMapper.map(project, ProjectDTO.class), "", HttpStatus.OK));
            Long increaseViewId = projectService.increaseView(id);
            return ResponseEntity.ok(new BaseResponse(increaseViewId, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(
                    null,
                    "Đã xảy ra lỗi khi lấy danh sách bài viết dự án. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
        }
    }
}

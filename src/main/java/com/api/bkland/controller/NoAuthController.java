package com.api.bkland.controller;

import com.api.bkland.entity.ChatRoom;
import com.api.bkland.entity.Message;
import com.api.bkland.payload.dto.ChatRoomDTO;
import com.api.bkland.payload.dto.MessageDTO;
import com.api.bkland.payload.dto.ReportTypeDTO;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.service.ChatService;
import com.api.bkland.service.ReportTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/no-auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NoAuthController {
    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ModelMapper modelMapper;

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
}

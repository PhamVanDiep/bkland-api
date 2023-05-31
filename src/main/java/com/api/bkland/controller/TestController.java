package com.api.bkland.controller;

import com.api.bkland.config.annotation.CurrentUser;
import com.api.bkland.payload.dto.UserDTO;
import com.api.bkland.entity.User;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.security.services.UserDetailsImpl;
import com.api.bkland.service.NotifyService;
import com.api.bkland.service.TestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private TestService testService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotifyService notifyService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> getAllUsers() {
        List<User> users = testService.getAllUsers();
        if (users.size() == 0) {
            return ResponseEntity.ok(new BaseResponse(null, "Khong tim thay user nao", HttpStatus.NOT_FOUND));
        } else {
            List<UserDTO> userDTOS = users
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new BaseResponse(users, "", HttpStatus.OK));
        }
    }

    @PostMapping("/notify")
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> notifyToAll() {
        try {
            notifyService.notifyToAllUsers("Hello world");
            return ResponseEntity.ok(new BaseResponse(null, "", HttpStatus.OK));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse(null, "", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/current-user")
    @PreAuthorize("hasRole('ROLE_AGENCY') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ENTERPRISE')")
    public ResponseEntity<BaseResponse> getCurrentUser(@CurrentUser UserDetailsImpl currentUser) {
        try {
//            UserDetails userDetails = (UserDetailsImpl) authentication.getDetails();
//            return ResponseEntity.ok(new BaseResponse(authentication.getDetails(), "", HttpStatus.OK));
            return ResponseEntity.ok(new BaseResponse(currentUser, "", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse(null, "", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}

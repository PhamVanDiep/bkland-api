package com.api.bkland.service;

import com.api.bkland.payload.dto.RoleDTO;
import com.api.bkland.payload.dto.UserDTO;
import com.api.bkland.repository.RoleRepository;
import com.api.bkland.repository.UserRepository;
import com.api.bkland.security.jwt.JwtUtils;
import com.api.bkland.security.services.UserDetailsImpl;
import com.api.bkland.config.exception.TokenRefreshException;
import com.api.bkland.constant.enumeric.ERole;
import com.api.bkland.entity.RefreshToken;
import com.api.bkland.entity.User;
import com.api.bkland.payload.request.LoginRequest;
import com.api.bkland.payload.request.TokenRefreshRequest;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.payload.response.JwtResponse;
import com.api.bkland.payload.response.TokenRefreshResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    public BaseResponse authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            return new BaseResponse(new JwtResponse(
                                            jwt,
                                            refreshToken.getToken(),
                                            userDetails.getId(),
                                            userDetails.getUsername(),
                                            userDetails.getEmail(),
                                            roles
                                        ),
                                "Đăng nhập thành công",
                                HttpStatus.OK
            );
        } catch (Exception e) {
            return new BaseResponse(null,
                    "Tên đăng nhập hoặc mật khẩu không chính xác",
                    HttpStatus.NOT_FOUND);
        }
    }

    public BaseResponse registerUser(UserDTO signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new BaseResponse(null, "Tên đăng nhập đã tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new BaseResponse(null, "Email đã được sử dụng", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        signupRequest.setPassword(encoder.encode(signupRequest.getPassword()));
        signupRequest.setCreateAt(Instant.now());

        if (signupRequest.getRoles().size() == 0) {
            Set<RoleDTO> roleDTOS = new HashSet<>();
            RoleDTO roleDTOTmp = new RoleDTO();
            roleDTOTmp.setId(1);
            roleDTOTmp.setName(ERole.ROLE_USER.toString());
            roleDTOS.add(roleDTOTmp);
            signupRequest.setRoles(roleDTOS);
        }

        // Create new user's account
        User user = convertToEntity(signupRequest);
//        Set<String> strRoles = signupRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case RoleValue.ROLE_ADMIN:
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//                        break;
//                    case RoleValue.ROLE_ENTERPRISE:
//                        Role modRole = roleRepository.findByName(ERole.ROLE_ENTERPRISE)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//                        break;
//                    case RoleValue.ROLE_AGENCY:
//                        Role agencyRole = roleRepository.findByName(ERole.ROLE_AGENCY)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(agencyRole);
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//        user.setRoles(roles);
        userRepository.save(user);
        return new BaseResponse(null, "Đăng ký tài khoản thành công", HttpStatus.OK);
    }

    public BaseResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername(), user.getId());
                    return new BaseResponse(
                            new TokenRefreshResponse(token, requestRefreshToken),
                            "", HttpStatus.OK
                    );
//                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    public BaseResponse emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return new BaseResponse(null, "", HttpStatus.NO_CONTENT);
        } else {
            if (user.get().getUsername().equals(user.get().getEmail() + "_user_bkland")) {
                return new BaseResponse(null, "", HttpStatus.OK);
            } else {
                return new BaseResponse(null,
                        "Email đã được sử dụng để đăng ký tài khoản trước đó.",
                        HttpStatus.BAD_REQUEST);
            }
        }
    }

    private User convertToEntity(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}

package com.modernadev.Moderna.Home.service.impl;

import com.modernadev.Moderna.Home.dto.LoginRequest;
import com.modernadev.Moderna.Home.dto.Response;
import com.modernadev.Moderna.Home.dto.UserDto;
import com.modernadev.Moderna.Home.entity.User;
import com.modernadev.Moderna.Home.enums.UserRole;
import com.modernadev.Moderna.Home.exception.InvalidCredentialException;
import com.modernadev.Moderna.Home.mapper.EntityDtoMapper;
import com.modernadev.Moderna.Home.repository.UserRepo;
import com.modernadev.Moderna.Home.security.JwtUtils;
import com.modernadev.Moderna.Home.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response registerUser(UserDto registrationRequest) {
        if (userRepo.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new InvalidCredentialException("Email already exists");
        }

        UserRole userRole = UserRole.USER;
        if (registrationRequest.getUserRole() != null && registrationRequest.getUserRole().equalsIgnoreCase("admin")) {
            userRole = UserRole.ADMIN;
        }

        // 3. Hash password và tạo user
        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .userRole(userRole)
                .build();

        // 4. Lưu DB
        User savedUser = userRepo.save(user);
        // 5. Map sang DTO không trả mật khẩu
        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);

        // 6. Trả về response
        return Response.builder()
                .status(200)
                .message("User registered successfully!")
                .userDto(userDto)
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new InvalidCredentialException("Invalid email"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Invalid password");
        }
        String token = jwtUtils.generateToken(user);
        return Response.builder()
                .status(200)
                .message("Login successful!")
                .token(token)
                .expirationTime("1 day")
                .role(user.getUserRole().name())
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtoList = users.stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .toList();
        if(userDtoList.isEmpty()){
            throw new InvalidCredentialException("No users found");
        }
        return Response.builder()
                .status(200)
                .message("Successfully!")
                .userDtoList(userDtoList)
                .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra xem user đã đăng nhập hợp lệ chưa
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            throw new AccessDeniedException("Bạn chưa đăng nhập hoặc phiên đã hết hạn");
        }
        String email = authentication.getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
        User user = getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderHistory(user);

        return Response.builder()
                .status(200)
                .userDto(userDto)
                .build();
    }
}

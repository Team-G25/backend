package com.g25.mailer.user.controller;

import com.g25.mailer.user.dto.AddUserRequest;
import com.g25.mailer.user.dto.AddUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.g25.mailer.user.common.CommonResponse;
import com.g25.mailer.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user")
    public CommonResponse<AddUserResponse> signup(@RequestBody AddUserRequest request) {
        Long id = userService.save(request);

        return CommonResponse.success(AddUserResponse.builder()
                .email(request.getEmail())
                .build());
    }

    // 로그아웃 (Spring Security 제거 후 직접 구현 필요)
    @GetMapping("/logout")
    public CommonResponse<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        // TODO: 세션 무효화 로직 추가 필요
        return CommonResponse.success();
    }

    // 로그인 (Spring Security 없이 직접 검증)
    @PostMapping("/login")
    public CommonResponse<AddUserResponse> login(@RequestBody AddUserRequest request) {
        // 직접 로그인 로직 구현
        boolean isAuthenticated = userService.authenticate(request.getEmail(), request.getPassword());
        if (!isAuthenticated) {
            throw new RuntimeException("로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.");
        }

        return CommonResponse.success(AddUserResponse.builder()
                .email(request.getEmail())
                .build());
    }

    // 유저 프로필 이미지 업로드
    @PostMapping("/{userId}/profile-image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long userId,
                                                     @RequestParam("file") MultipartFile file) {
        String imageUrl = userService.uploadProfileImage(userId, file);
        return ResponseEntity.ok(imageUrl);
    }

    // 유저 프로필 이미지 삭제
    @DeleteMapping("/{userId}/profile-image")
    public ResponseEntity<String> deleteProfileImage(@PathVariable Long userId) {
        userService.deleteProfileImage(userId);
        return ResponseEntity.ok("기본 이미지로 설정하였습니다.");
    }
}

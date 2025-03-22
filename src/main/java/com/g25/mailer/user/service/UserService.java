package com.g25.mailer.user.service;

import com.g25.mailer.user.common.CommonResponse;
import com.g25.mailer.user.dto.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * image 첨부
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String PROFILE_IMAGE_DIR = "uploads/profile_images/";

    /**
     * 회원가입, 회원가입 후 나중에 유저가 프로필 이미지 등록하도록 한다.
     * @param request
     * @return
     */
    public CommonResponse<Map<String, String>> save(AddUserRequest request) {
        User user = userRepository.save(User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .theme(User.Theme.LIGHT)
                .build());

        Map<String, String> responseData = new HashMap<>();
        responseData.put("nickname", user.getNickname());
        responseData.put("email", user.getEmail());

        return CommonResponse.success(responseData);
    }

    /**
     * 로그인
     * @param email
     * @param password
     * @return
     */
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        } //로그인 검증

        return LoginResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }



    /**
     * 로그아웃
     * @param session
     * @return
     */
    public CommonResponse<String> logout(HttpSession session) {
        session.invalidate(); //세션 무효화
        return CommonResponse.success("로그아웃");
    }

    /**
     * 유저 프로필 이미지 업로드, 회원가입 후 나중에 유저가 프로필 이미지 등록하도록 한다.
     * @param userId
     * @param file
     * @return
     */
    public String uploadProfileImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("없는 유저 정보입니다."));

        // 디렉토리 없으면 생성
        File uploadDir = new File(PROFILE_IMAGE_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 랜덤 UUID + 원본 확장자로 저장
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + extension;

        File destinationFile = new File(PROFILE_IMAGE_DIR + newFileName);
        try {
            file.transferTo(destinationFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile image", e);
        }

        // 기존 이미지 삭제 (새 이미지로 교체할 경우)
        if (user.getProfileImageUrl() != null) {
            File oldFile = new File(user.getProfileImageUrl());
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        // DB에 새 이미지 경로 저장
        user.setProfileImageUrl(PROFILE_IMAGE_DIR + newFileName);
        userRepository.save(user);

        return user.getProfileImageUrl();
    }


    /**
     * 유저 비밀번호 변경
     * @param userId
     * @param newPassword
     * @return
     */
    public CommonResponse<String> changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return CommonResponse.success("비밀번호가 변경되었습니다.");
    }



    /**
     * 환경설정 > 테마(다크, 라이트)
     * @param theme "theme": "DARK" OR "theme": "LIGHT"
     * @param session
     * @return
     */
    @PostMapping("/change-theme")
    public ResponseEntity<CommonResponse<String>> changeTheme(@RequestParam User.Theme theme, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body(CommonResponse.fail("로그인이 필요합니다."));
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(CommonResponse.fail("유저를 찾을 수 없습니다."));
        }

        user.setTheme(theme);
        userRepository.save(user);

        return ResponseEntity.ok(CommonResponse.success("테마가 변경되었습니다."));
    }




    /**
     * userId 가져온다
     * 로그인할때 사용
     * @param email
     * @return
     */
    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."))
                .getId();
    }


}

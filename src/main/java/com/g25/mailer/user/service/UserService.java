package com.g25.mailer.user.service;

import com.g25.mailer.common.CommonResponse;
import com.g25.mailer.common.service.S3Uploader;
import com.g25.mailer.user.dto.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * image 첨부
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private static final String PROFILE_IMAGE_DIR = "uploads/profile_images/";

    /**
     * 회원가입, 회원가입 후 나중에 유저가 프로필 이미지 등록하도록 한다.
     *
     * @param request
     * @param file
     * @return
     */
    public User save(AddUserRequest request, MultipartFile file) {
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .theme(User.Theme.LIGHT)
                .build();

        if (file != null && !file.isEmpty()) {
            String fileKey = s3Uploader.uploadProfileImg(file);
            String imageUrl = "https://" + bucket + ".s3.amazonaws.com/" + fileKey;
            user.setProfileImageUrl(imageUrl);
        }

        return userRepository.save(user);
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

        // S3에 이미지 업로드
        String fileKey = s3Uploader.uploadProfileImg(file);

        // 기존 이미지 삭제 (있다면)
        if (user.getProfileImageUrl() != null) {
            String oldKey = extractKeyFromUrl(user.getProfileImageUrl());
            s3Uploader.deleteObject(oldKey);
        }

        // S3 URL 구성
        String imageUrl = "https://" + bucket + ".s3.amazonaws.com/" + fileKey;
        user.setProfileImageUrl(imageUrl);
        userRepository.save(user);

        return imageUrl;
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


    /**e
     * 유저 프로필 이미지 삭제
     */
    @Transactional
    public void deleteProfileImage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String key = extractKeyFromUrl(user.getProfileImageUrl()); //S3에서 삭제할 파일명확장자포함(key) 추출

        if (key != null) {
            s3Uploader.deleteObject(key); // S3에서 삭제
        }
        user.setProfileImageUrl(null); // DB에서 제거
    }

    /**
     * 파일명과 확장자만 골라오기
     * @param profileImageUrl
     * @return
     */
    // 예: https://bucket.s3.amazonaws.com/profile-img/abc.jpg → profile-img/abc.jpg
    private String extractKeyFromUrl(String profileImageUrl) {
        if (profileImageUrl == null || profileImageUrl.isBlank()) return null;
        try {
            URL parsedUrl = new URL(profileImageUrl);
            return parsedUrl.getPath().substring(1); // 맨 앞 '/' 제거
        } catch (MalformedURLException e) {
            return null;
        }
    }


}

package com.g25.mailer.user.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.g25.mailer.user.dto.AddUserRequest;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * image 첨부, 약관동의 미구현, 작성 예정
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String PROFILE_IMAGE_DIR = "uploads/profile_images/";

    public Long save(AddUserRequest dto) {
        return userRepository.save(
                User.builder()
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .build()).getId();
    }

    public String uploadProfileImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

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

    public void deleteProfileImage(Long userId) {
    }


    public boolean authenticate(@NotNull String email, @NotNull String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordEncoder.matches(password, user.getPassword());
    }

}

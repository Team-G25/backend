package com.g25.mailer.user.controller;

import com.g25.mailer.user.dto.AddUserRequest;
import com.g25.mailer.user.dto.AddUserResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.g25.mailer.user.common.CommonResponse;
import com.g25.mailer.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // [QUIZ] 아래 에러 수정하기 - 완료
    //회원가입
    @PostMapping("/user")
    public CommonResponse<AddUserResponse> signup(@RequestBody AddUserRequest request) {
        Long id = userService.save(request); // 회원 저장 메서드 호출

        return CommonResponse.success(AddUserResponse.builder()
                .email(request.getEmail())
                .build());
    }


    //로그아웃
    @GetMapping("/logout")
    public CommonResponse<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler()
                .logout(request, response, SecurityContextHolder.getContext().getAuthentication()); //핸들러가 logout메서드 호출 & 로그아웃
        return CommonResponse.success();
    }


    //로그인


//    // CREATE: 사용자 등록
//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        if (userRepository.existsByLoginId(user.getLoginId()) || userRepository.existsByEmail(
//                user.getEmail())) {
//            return ResponseEntity.badRequest().body(null);
//        }
//        User savedUser = userRepository.save(user);
//        return ResponseEntity.created(URI.create("/api/users/" + savedUser.getId())).body(savedUser);
//    }


//
//    // READ: 특정 사용자 조회
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        Optional<User> user = userRepository.findById(id);
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // UPDATE: 사용자 정보 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
//        return userRepository.findById(id).map(existingUser -> {
//            existingUser.setName(updatedUser.getName());
//            existingUser.setEmail(updatedUser.getEmail());
//            existingUser.setContact(updatedUser.getContact());
//            existingUser.setRole(updatedUser.getRole());
//            existingUser.setStatus(updatedUser.getStatus());
//            User savedUser = userRepository.save(existingUser);
//            return ResponseEntity.ok(savedUser);
//        }).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // DELETE: 사용자 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        if (userRepository.existsById(id)) {
//            userRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}

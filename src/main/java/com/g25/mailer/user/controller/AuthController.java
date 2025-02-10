package com.g25.mailer.User.controller;

import com.g25.mailer.User.dto.UserLoginReq;
import com.g25.mailer.User.dto.UserSessionRes;
import com.g25.mailer.User.entity.User;
import com.g25.mailer.User.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    /**
     * Session 인증방식 login
     */

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginReq request, HttpSession session) {
        User user = authService.authenticate(request.getLoginId(), request.getPassword());
        session.setAttribute("user", new UserSessionRes(user.getLoginId(), user.getName(), user.getRole())); //세션에 민감 정보 저장 금지
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); // 세션 삭제
        return ResponseEntity.ok("로그아웃 성공");
    }
}

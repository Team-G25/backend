package com.g25.mailer.user.controller;

import com.g25.mailer.user.dto.EmailRequest;
import com.g25.mailer.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;


    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody EmailRequest request) {
        emailService.sendMail(request.getTo(), request.getSubject(), request.getContent());
        return ResponseEntity.ok("메일이 성공적으로 전송되었습니다.");
    }
}

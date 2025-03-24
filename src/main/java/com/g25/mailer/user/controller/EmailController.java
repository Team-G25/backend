package com.g25.mailer.user.controller;

import com.g25.mailer.user.dto.EmailRequest;
import com.g25.mailer.user.service.EmailService;
import com.g25.mailer.user.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * POST /mail/send
 * → multipart/form-data로 요청
 * → 컨트롤러 내부에서 파일 있으면 S3 업로드
 * → 업로드된 파일을 S3에서 다시 읽어 첨부
 * → 메일 전송
 */

/**
 * 구조
 * [ EmailController ]
 *       ↓
 * [ S3Uploader ] ← MultipartFile 업로드
 * [ S3Service ]  ← getObject(key), deleteObject(key)
 *       ↓
 * [ EmailService ] ← 첨부 포함 메일 전송
 */

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;
    private final S3Uploader s3Uploader;


    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@ModelAttribute EmailRequest request) {
        List<String> fileKeys = new ArrayList<>();

        //첨부파일이 있는 경우 S3 업로드
        if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
            fileKeys = s3Uploader.uploadMultipleToS3(request.getAttachments(), "attachments");
        }

        // 첨부 유무에 따라 메일 전송 방식 분기
        if (!fileKeys.isEmpty()) {
            emailService.sendMailWithAttachment(
                    request.getTo(),
                    request.getSubject(),
                    request.getContent(),
                    request.getFrom(),
                    fileKeys
            );
        } else {
            emailService.sendSimpleMail(
                    request.getTo(),
                    request.getSubject(),
                    request.getContent(),
                    request.getFrom()
            );
        }

        return ResponseEntity.ok("메일 전송 완료");
    }




}

package com.g25.mailer.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * 메일 송신 기능
     * @param to 수신자
     * @param title 제목
     * @param content 내용
     * @param from 송신자
     */
    public void sendMail(String to, String title, String content, String from) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 송신자가 미입력시에 디폴트값
            String senderEmail = (from != null) ? from : "no-reply@temporary.com";


            /**
             * helper 필요함 -> 보내는사람, 받는사람 적용됨
             * helper.setTo(); //받는사람
             * helper.setFrom(); //보내는사람
             */
            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("메일이 전송 완료. 받는 사람: {}", to);
        } catch (MessagingException e) {
            log.error("메일 전송 실패: {}", e.getMessage());
            throw new RuntimeException("메일 전송 오류 발생");
        }
    }
}

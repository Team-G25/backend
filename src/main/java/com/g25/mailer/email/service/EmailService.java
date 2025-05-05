package com.g25.mailer.email.service;

import com.amazonaws.services.s3.model.S3Object;
import com.g25.mailer.common.service.S3Service;
import com.g25.mailer.email.exception.SendGridEmailException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.mail.MessagingException; //더 이상 사용하지 않음 gmail용
import jakarta.mail.internet.MimeMessage; //더 이상 사용하지 않음 gmail용

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;


/**
 * 👽 Gmail API -> SendGrid API에서 변경사항
 * ✅ Gmail SMTP용 MimeMessage → ❌ 제거
 * ✅ SendGrid API 전송 → ✅ 추가
 * ✅ 첨부파일 InputStream 처리 → ✅ Base64 변환 후 SendGrid Attachments로 추가
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    //private final JavaMailSender mailSender; //이제 SendGrid로 전환했으니 필요 없음
    private final S3Service s3Service;

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;



    /**
     * [SendGrid] 기본 Mail 객체 생성
     *  -> [Gmail] MimeMessageHelper 직접 만들던 부분을 분리
     */
    private Mail buildBasicMail(String to, String title, String content, String from) {
        Email fromEmail = new Email(from != null ? from : "no-reply@mailer.com");
        Email toEmail = new Email(to);
        Content mailContent = new Content("text/plain", content);
        return new Mail(fromEmail, title, toEmail, mailContent);
    }
    // Mail 클래스는 SendGrid Java SDK에 내장되어 있는 클래스이며 라이브러리에 추가함

    /**
     * SendGrid 전송, 실제 메일 송신 - 커스텀예외객체 사용함.
     */
    private void sendWithSendGrid(Mail mail) {
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 400) {
                log.error("SendGrid 전송 실패 - 상태코드: {}, 응답: {}", response.getStatusCode(), response.getBody());
                throw new SendGridEmailException("SendGrid 전송 실패 - 상태코드: " + response.getStatusCode());
            }

            log.info("SendGrid Headers: {}", response.getHeaders());
        } catch (IOException e) {
            log.error("SendGrid 전송 실패: {}", e.getMessage(), e);
            throw new SendGridEmailException("SendGrid 전송 중 IOException 발생", e);
        }
    }



    /**
     * [SendGrid] S3 파일 Base64 변환 메서드
     *  -> 첨부파일 전송할 때만 호출됨
     */
    private String getBase64FileContentFromS3(String key) {
        S3Object s3Object = s3Service.getObject(bucket, key);

        try (InputStream inputStream = s3Object.getObjectContent()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 읽기 실패", e);
        }
    }

    /**
     * [SendGrid] 첨부파일없는 메일 송신, sendSimpleMail
     * 기존 MimeMessageHelper 작성 코드 → SendGrid 방식으로 교체
     */
    public void sendSimpleMail(String to, String title, String content, String from) {
        Mail mail = buildBasicMail(to, title, content, from);
        sendWithSendGrid(mail);
    }

    /**
     * [SendGrid] 첨부파일있는 메일 송신, sendMailWithAttachment
     *  -> 기존 MimeMessageHelper + InputStreamSource → SendGrid Attachments로 교체
     */
    public void sendMailWithAttachment(String to, String title, String content, String from, List<String> fileKeys) {
        Mail mail = buildBasicMail(to, title, content, from);

        for (String key : fileKeys) {
            String filename = key.substring(key.lastIndexOf("/") + 1);
            String base64Content = getBase64FileContentFromS3(key);

            Attachments attachment = new Attachments();
            attachment.setFilename(filename);
            attachment.setContent(base64Content);
            attachment.setType("application/octet-stream");
            attachment.setDisposition("attachment");

            mail.addAttachments(attachment);
        }

        sendWithSendGrid(mail);
    }




    /**
     * [Gmail]메일 송신 기능
     * @param to 수신자
     * @param title 제목
     * @param content 내용
     * @param from 송신자
     */
    /**
    public void sendSimpleMail(String to, String title, String content, String from) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");


//             helper 필요함 -> 보내는사람, 받는사람 적용됨
//             helper.setTo(); //받는사람
//             helper.setFrom(); //보내는사람

            String senderEmail = (from != null) ? from : "no-reply@temporary.com"; // 송신자가 미입력시에 디폴트값

            helper.setFrom(senderEmail);
            if (from != null && !from.isBlank()) {
                helper.setReplyTo(from);
            }
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
    */


    /**
     * 첨부파일 포함 메일 송신
     * @param to
     * @param title
     * @param content
     * @param fileKeys
     */
    /**
    public void sendMailWithAttachment(String to, String title, String content, String from, List<String> fileKeys) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String senderEmail = (from != null) ? from : "no-reply@temporary.com"; // 송신자가 미입력시에 디폴트값

            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content, true);

            for (String key : fileKeys) {
                String originalFilename = key.substring(key.lastIndexOf("/") + 1);

                // InputStreamSource가 새 InputStream을 반환하도록 수정
                helper.addAttachment(originalFilename, (InputStreamSource) () -> {
                    return s3Service.getObject(bucket, key).getObjectContent();
                });
            }

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("메일 전송 중 오류가 발생했습니다.", e);
        }


    }
     */




}

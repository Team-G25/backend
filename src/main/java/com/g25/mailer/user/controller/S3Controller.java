package com.g25.mailer.user.controller;

import com.g25.mailer.user.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class S3Controller {

    private final S3Service s3Service;

    /**
     * 다중 파일 업로드
     * @param multipartFiles
     * @return
     */
    @PostMapping("/upload/muliples")
    public ResponseEntity<List<String>> uploadMultiple(List<MultipartFile> multipartFiles){
        return ResponseEntity.ok(s3Service.uploadMultiple(multipartFiles));
    }

    /**
     * 유저 프로필 이미지 -> 단일 파일 업로드
     * 단건 s3버킷에 업로드
     */
    @PostMapping("/upload/one")
    public ResponseEntity<String> uploadFile(MultipartFile multipartFile){
        return ResponseEntity.ok((s3Service.uploadSingle(multipartFile)));
    }

    /**
     * 업로드한 파일 삭제
     * @param fileName
     * @return
     */
    @DeleteMapping("/out")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName){
        s3Service.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }

}

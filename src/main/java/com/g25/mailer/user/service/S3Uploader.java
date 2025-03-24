package com.g25.mailer.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * MultipartFile을 S3에 업로드하는 역할 (업로드 전용)
 */
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //단일 업로드
    public String uploadToS3(MultipartFile file, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3.putObject(
                    new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
            );

            return fileName; // fileKey
        } catch (IOException e) {
            throw new RuntimeException("S3 업로드를 실패하였습니다.", e);
        }
    }


    // 다중 파일 업로드 (디렉토리 포함, 유연하게 사용 가능)
    public List<String> uploadMultipleToS3(List<MultipartFile> multipartFiles, String dirName) {
        List<String> fileKeyList = new ArrayList<>();

        if (multipartFiles == null || multipartFiles.isEmpty()) return fileKeyList;

        for (MultipartFile file : multipartFiles) {
            if (file == null || file.isEmpty()) continue;

            String fileName = dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                s3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패", e);
            }

            fileKeyList.add(fileName);
        }

        return fileKeyList;
    }



    //유저프로필 등록
    public String uploadProfileImg(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        return uploadToS3(multipartFile, "profile-img");
    }



    // S3 객체 삭제
    public void deleteObject(String key) {
        if (key == null || key.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제할 파일 경로가 유효하지 않습니다.");
        }

        if (!s3.doesObjectExist(bucket, key)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "S3에 해당 파일이 존재하지 않습니다.");
        }

        s3.deleteObject(bucket, key);
    }



}


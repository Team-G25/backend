package com.g25.mailer.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * S3에서 객체를 조회하거나 삭제하는 등 파일 관리 로직 (조회, 삭제 등)
 */
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //파일삭제
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    //파일읽어오기
    public S3Object getObject(String key) {
        return amazonS3.getObject(new GetObjectRequest(bucket, key));
    }

    //파일읽어오기
    public S3Object getObject(String bucket, String key) {
        return amazonS3.getObject(bucket, key);
    }

    //삭제 기능
    public void deleteObject(String key) {
        amazonS3.deleteObject(bucket, key);
    }



}
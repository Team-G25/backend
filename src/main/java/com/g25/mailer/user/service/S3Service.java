package com.g25.mailer.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 단일 업로드 -> 유저 프로필
     * @param multipartFile
     * @return
     */
    public String uploadSingle(MultipartFile multipartFile){

        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        return fileName;
    }


    /**
     * 다중업로드
     * @param multipartFiles
     * @return
     */
    public List<String> uploadMultiple(List<MultipartFile> multipartFiles){
        List<String> fileNameList = new ArrayList<>();

        // forEach 구문을 통해 multipartFiles 리스트로 넘어온 파일들을 순차적으로 fileNameList 에 추가
        multipartFiles.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()){
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
            } catch (IOException e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
            fileNameList.add(fileName);

        });

        return fileNameList;
    }



    //파일 난수화 -> 보안
    private String createFileName(String originalFilename) {
        return UUID.randomUUID().toString() + getFileExtension(originalFilename);
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }


    /**
     * 파일 확장자 추출,    "."의 존재 유무만 판단
     */
    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일: " + fileName);
        }
        return fileName.substring(index);
    }



}
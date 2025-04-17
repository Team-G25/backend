package com.g25.mailer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g25.mailer.config.feign.CustomErrorDecorder;
import feign.Logger;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Configuration
public class GptFeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    //Feign의 로그 레벨 설정 >  FULL : 요청 메서드, URL, 헤더, 바디까지 모두 출력

    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 10000); // ms
    }
    //Feign의 타임아웃 설정 > 5000 ms (5초) → 연결 타임아웃, 10000 ms (10초) → 응답 대기 타임아웃

/**
    //구버전 디코더
    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        return (response, type) -> {
            // 응답 바디가 비어 있으면, 예외를 발생
            if (response.body() == null) {
                return  new RuntimeException("응답 에러: " + response.status());
            }
            //gpt 에러 : HTTP 상태 코드가 400 이상이면, 에러 응답으로 간주하고 예외
            if (response.status() >= 400) {
                throw new RuntimeException("OpenAI 에러: " + response.status());
            }
            //objectMapper : JSON을 Java 객체로 변환
            try (Reader reader = response.body().asReader(StandardCharsets.UTF_8)) {
                return objectMapper.readValue(reader, objectMapper.constructType(type));
            }
        };
    }
 */

    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        return (response, type) -> {
            if (response.body() == null) {
                return null;
            }

            try (Reader reader = response.body().asReader(StandardCharsets.UTF_8)) {
                return objectMapper.readValue(reader, objectMapper.constructType(type));
            }
        };
    }
    //커스텀 디코더 > 응답 바디를 UTF-8로 읽어 JSON을 Java 객체로 파싱(변환)

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecorder();
    }
    //기존디코더에서 에러디코드 별도로 생성하여 에러처리

}

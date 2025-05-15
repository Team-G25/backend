package com.g25.mailer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g25.mailer.config.feign.CustomErrorDecorder;
import feign.Logger;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.aopalliance.intercept.Interceptor;
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

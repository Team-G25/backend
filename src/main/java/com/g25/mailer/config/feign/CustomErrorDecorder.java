package com.g25.mailer.config.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecorder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("{} 요청이 성공하지 못했습니다. status : {}, body :{}", methodKey, response.status(), response.body());
        int status = response.status();

        if (status >= 400 && status < 500) {
            return new RuntimeException("GPT 클라이언트 오류: " + status);
        }
        else if (status >= 500) {
            return new RuntimeException("GPT 서버 오류 : " + status);
        }
        return new RuntimeException("GPT 미상의 오류 : " + status);
    }


}

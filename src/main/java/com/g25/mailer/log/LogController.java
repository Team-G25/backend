package com.g25.mailer.log;

import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LogController {
    // 임포트 파일 주의!
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Hidden
    @GetMapping("/log")
    public String logTest(){
        String name = "spring";

        log.error("❌error log={}",name);
        log.warn("🥵warn log={}",name);
        log.info("🥑info log={}",name);
        log.debug("☠️debug log={}",name);
        log.trace("🐭trace log={}",name);

        return "로그 테스트 완료!";
    }

}

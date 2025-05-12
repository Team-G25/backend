package com.g25.mailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {
		"com.g25.mailer.gptAi.gpt",     // 기존 GPT 내부 호출용
		"com.g25.mailer.mailerAi"        // 외부 AI 서버 연동용
})
@SpringBootApplication
public class MailerApplication {
 	//change email API
	public static void main(String[] args) {
		SpringApplication.run(MailerApplication.class, args);
	}

}

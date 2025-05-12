package com.g25.mailer.mailerAi.controller;

import com.g25.mailer.mailerAi.dto.AiGeneratedMailResponse;
import com.g25.mailer.mailerAi.dto.AiPromptRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "aiTextClient", url = "${ai.server.url}")
public interface AiTextGeneratorClient {

    @PostMapping("/ai/generate")
    AiGeneratedMailResponse generate(@RequestBody AiPromptRequest request);
}
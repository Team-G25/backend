package com.g25.mailer.mailerAi.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import com.g25.mailer.mailerAi.dto.AiGeneratedMailResponse;
import com.g25.mailer.mailerAi.dto.AiPromptRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mailer-ai")
@Tag(name = "Mailer AI", description = "직접 만든 메일러 AI 모델 기반 자동 이메일 생성 API")
public class AiTextGenerationController {

    private final AiTextGeneratorClient aiClient;



    @Operation(
            summary = "메일러 AI 메일 자동 생성",
            description = "메일러 AI 서버와 통신하여 메일을 생성합니다.",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AiPromptRequest.class),
                            examples = @ExampleObject(value = "{ \"prompt\": \"교수님께 성적 정정요청한다고 정중한 메일보내\" }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "메일러 AI가 생성한 메일 반환",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AiGeneratedMailResponse.class),
                                    examples = @ExampleObject(value = "{ \"content\": \"안녕하세요, ...\" }")
                            )
                    ),
                    @ApiResponse(responseCode = "503", description = "메일러 AI 서버 응답 실패")
            }
    )
    @PostMapping("/auto-generate")
    public ResponseEntity<AiGeneratedMailResponse> generate(@RequestBody AiPromptRequest request) {
        try {
            return ResponseEntity.ok(aiClient.generate(request));
        } catch (Exception e) {
            log.error("메일러 AI 서버 호출 실패", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new AiGeneratedMailResponse("메일러 AI 응답 실패"));
        }
    }
}


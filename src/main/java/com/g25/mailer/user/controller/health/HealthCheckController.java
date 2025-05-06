package com.g25.mailer.user.controller.health;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class HealthCheckController {
    @Hidden
    @GetMapping("/api/health")
    public String healthCheck() {
        return "check Completed";
    }



}

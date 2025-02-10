package com.g25.mailer.user.controller.health;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/api/health")
    public String healthCheck() {
        return "check Completed";
    }



}

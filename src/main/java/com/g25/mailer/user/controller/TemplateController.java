package com.g25.mailer.user.controller;

import com.g25.mailer.user.entity.Template;
import com.g25.mailer.user.service.TemplateService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping("/get")
    public Optional<Template> getTemplate(
            @RequestParam String target,
            @RequestParam String keyword1,
            @RequestParam String keyword2) {

        Optional<Template> tmpl = templateService.getTemplate(target, keyword1, keyword2);
        return tmpl;
    }






}

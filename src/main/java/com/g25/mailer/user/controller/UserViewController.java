package com.g25.mailer.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    // /login 로 들어오는 GET 요청을 이 메서드가 처리합니다
    // login.html 을 출력한다
    // 로그인을 진행하는 @PostMapping방식 메서드는 스프링시큐리티가 대신 처리하므로 우리가 직접 코드를 작성하지 x
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

}

package com.g25.mailer.user.controller;

import com.g25.mailer.common.CommonResponse;
import com.g25.mailer.user.dto.UserProfileResponse;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;


    @GetMapping("/")
    public String home() {
        return "<pre style='font-family: monospace; font-size: 14px;'>" +
                " __     __   ______   __       ______   ______   __    __   ______      ______  ______    \n" +
                "/\\ \\  _ \\ \\ /\\  ___\\ /\\ \\     /\\  ___\\ /\\  __ \\ /\\ \"-./  \\ /\\  ___\\    /\\__  _\\/\\  __ \\   \n" +
                "\\ \\ \\/ \".\\ \\\\ \\  __\\ \\ \\ \\____\\ \\ \\____\\ \\ \\/\\ \\\\ \\ \\-./\\ \\\\ \\  __\\    \\/_/\\ \\/\\ \\ \\/\\ \\  \n" +
                " \\ \\__/\".~\\_\\\\ \\_____\\\\ \\_____\\\\ \\_____\\\\ \\_____\\\\ \\_\\ \\ \\_\\\\ \\_____\\     \\ \\_\\ \\ \\_____\\ \n" +
                "  \\/_/   \\/_/ \\/_____/ \\/_____/ \\/_____/ \\/_____/ \\/_/  \\/_/ \\/_____/      \\/_/  \\/_____/ \n" +
                "                                                                                          \n" +
                " __    __   ______   __   __       ______   ______                                        \n" +
                "/\\ \"-./  \\ /\\  __ \\ /\\ \\ /\\ \\     /\\  ___\\ /\\  == \\                                       \n" +
                "\\ \\ \\-./\\ \\\\ \\  __ \\\\ \\ \\\\ \\ \\____\\ \\  __\\ \\ \\  __<                                       \n" +
                " \\ \\_\\ \\ \\_\\\\ \\_\\ \\_\\\\ \\_\\\\ \\_____\\\\ \\_____\\\\ \\_\\ \\_\\                                     \n" +
                "  \\/_/  \\/_/ \\/_/\\/_/ \\/_/ \\/_____/ \\/_____/ \\/_/ /_/                                     \n" +
                "                                                                                          \n" +
                "                                                                                          \n" +
                "                                                                                          \n" +
                "</pre>";
    }


    /**
     * 유저 이미지, 닉네임(이름) 받아오기
     * @param session
     * @return
     */
    @GetMapping("/profile")
    public ResponseEntity<CommonResponse<UserProfileResponse>> getUserProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        //로그인 안된 경우
        if (userId == null) {
            return ResponseEntity.badRequest().body(
                    CommonResponse.fail(new UserProfileResponse("로그인이 필요합니다.", null))
            );
        }

        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    CommonResponse.fail(new UserProfileResponse("유저를 찾을 수 없습니다.", null))
            );
        }

        User user = userOpt.get();
        UserProfileResponse response = new UserProfileResponse(user.getNickname(), user.getProfileImageUrl());

        return ResponseEntity.ok(CommonResponse.success(response));
    }



}

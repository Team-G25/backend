package com.g25.mailer.user.controller;

import com.g25.mailer.common.CommonResponse;
import com.g25.mailer.user.dto.UserProfileResponse;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
//import org.apache.logging.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "홈/유저 프로필 API", description = "홈 페이지 및 현재 로그인한 유저 정보 조회 API")
public class HomeController {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);


    @Hidden
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
    @Operation(summary = "유저 프로필 조회", description = "세션에 저장된 userId로 유저의 닉네임과 프로필 이미지를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 프로필 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "로그인 안됨 또는 유저 정보 없음")
    })
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

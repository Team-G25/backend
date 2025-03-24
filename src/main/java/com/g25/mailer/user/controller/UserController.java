package com.g25.mailer.user.controller;

import com.g25.mailer.user.dto.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.g25.mailer.common.CommonResponse;
import com.g25.mailer.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입, 이미지 업로드 x(이미지업로드 추가로 해야함)
     * @param request
     * @return AddUserResponse
     */
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Map<String, String>>> signup(@Valid @RequestBody AddUserRequest request) {
        log.info("[확인용] 회원가입 요청 : {}", request);
        return ResponseEntity.ok(userService.save(request));
    }


    /**
     * 로그아웃 : Spring Security제거하고 구현해야함
     * @param request
     * @return CommonResponse
     */
    @GetMapping("/logout")
    public ResponseEntity<CommonResponse<String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return ResponseEntity.ok(userService.logout(session));
        }
        return ResponseEntity.ok(CommonResponse.success("이미 로그아웃된 상태입니다."));
    }


    /**
     * 로그인 (Spring Security 없이 직접 검증), 세션 처리
     * @param request
     * @param httpRequest
     * @return AddUserResponse
     */
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        LoginResponse response = userService.login(request.getEmail(), request.getPassword());

        HttpSession session = httpRequest.getSession(true); //새션조회, 없으면 생성
        Long userId = userService.getUserIdByEmail(request.getEmail());
        session.setAttribute("userId",userId); //세션에 담음

        return ResponseEntity.ok(CommonResponse.success(response));
    }


    /**
     * TODO : 유저 프로필 사진 업로드 (서버사이드)
     * 클라이언트 사이드
     *  클라이언트쪽에서 s3 URL를 서버로 넘겨준다 -> 서버는 String으로 들어온 URL 를 저장
     *
     *
     * 서버사이드 <- 이걸로 구현
     * 1. 클라이언트가 multipart/form-data로 요청
     * 2. 서버가 S3Uploader.uploadProfileImg() 호출
     * 3. 해당 URL을 DB에 저장 (→ service에서 처리)
     *
     * @param
     * @param session
     * @return
     */
    @PostMapping("/setting/img")
    public ResponseEntity<CommonResponse<String>> uploadProfileImage(
            @RequestParam("file") MultipartFile file,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(CommonResponse.fail("로그인이 필요합니다."));
        }

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(CommonResponse.fail("이미지 파일을 업로드해주세요."));
        }

        String url = userService.uploadProfileImage(userId, file);
        return ResponseEntity.ok(CommonResponse.success(url));
    }



    /**
     * 유저 프로필 이미지 삭제(파일 삭제)
     * @param session
     * @return
     */
    @DeleteMapping("/setting/delete/img")
    public ResponseEntity<CommonResponse<String>> deleteProfileImage(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(CommonResponse.fail("로그인이 필요합니다."));
        }

        userService.deleteProfileImage(userId);
        return ResponseEntity.ok(CommonResponse.success("프로필 이미지가 삭제되었습니다."));
    }


    /**
     *비밀번호 변경
     */
    @PostMapping("/setting/account")
    public ResponseEntity<CommonResponse<String>> changePassword(
            @RequestBody @Valid ChangePasswordRequest request,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).body(CommonResponse.fail("로그인이 필요합니다."));
        }

        return ResponseEntity.ok(userService.changePassword(userId, request.getNewPassword()));
    }


}

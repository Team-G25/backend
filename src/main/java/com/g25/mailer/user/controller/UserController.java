package com.g25.mailer.user.controller;

import com.g25.mailer.user.dto.*;
import com.g25.mailer.user.service.UserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "회원 API", description = "회원가입, 로그인, 로그아웃, 프로필 설정, 비밀번호 변경 등 회원 관련 API")
public class UserController {

    private final UserService userService;
    private final UserDetailService userDetailService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 요청을 처리합니다." +
            "{\n" +
            "  \"nickname\": \"admin\",\n" +
            "  \"email\": \"1234@mailergo.io.kr\",\n" +
            "  \"password\": \"1234\"\n" +
            "}\n ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<CommonResponse<Map<String, String>>> signup(@Valid @RequestBody AddUserRequest request) {
        log.info("[확인용] 회원가입 요청 : {}", request);
        return ResponseEntity.ok(userService.save(request));
    }


    @GetMapping("/logout")
    @Operation(summary = "로그아웃", description = "세션을 제거하여 로그아웃을 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공 또는 이미 로그아웃된 상태")
    })
    public ResponseEntity<CommonResponse<String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return ResponseEntity.ok(userService.logout(session));
        }
        return ResponseEntity.ok(CommonResponse.success("이미 로그아웃된 상태입니다."));
    }


    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하고 세션에 userId를 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
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
     */


    @PostMapping("/setting/img")
    @Operation(summary = "프로필 이미지 업로드", description = "multipart/form-data로 이미지를 업로드하고 S3에 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이미지 업로드 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "로그인 필요")
    })
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



    @DeleteMapping("/setting/delete/img")
    @Operation(summary = "프로필 이미지 삭제", description = "S3에서 프로필 이미지를 삭제하고 DB 기록을 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 이미지 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 필요")
    })
    public ResponseEntity<CommonResponse<String>> deleteProfileImage(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(CommonResponse.fail("로그인이 필요합니다."));
        }

        userService.deleteProfileImage(userId);
        return ResponseEntity.ok(CommonResponse.success("프로필 이미지가 삭제되었습니다."));
    }


    @PostMapping("/setting/account")
    @Operation(summary = "비밀번호 변경", description = "현재 로그인된 사용자의 비밀번호를 새 비밀번호로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 필요")
    })
    public ResponseEntity<CommonResponse<String>> changePassword(
            @RequestBody @Valid ChangePasswordRequest request,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).body(CommonResponse.fail("로그인이 필요합니다."));
        }

        return ResponseEntity.ok(userService.changePassword(userId, request.getNewPassword()));
    }


    @GetMapping("/check-email")
    @Operation(summary = "회원가입 여부 확인", description = "이메일로 회원가입 여부만 체크합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "가입 여부 조회 성공")
    })
    public ResponseEntity<CommonResponse<Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = userDetailService.checkEmailExists(email);
        return ResponseEntity.ok(CommonResponse.success(exists));
    }



}

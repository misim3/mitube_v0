package com.misim.controller;

import com.misim.controller.model.Request.*;
import com.misim.controller.model.Response.FindNicknameResponse;
import com.misim.controller.model.Response.VerifySMSResponse;
import com.misim.exception.CommonResponse;
import com.misim.service.SmsService;
import com.misim.service.UserService;
import com.misim.service.VerificationTokenService;
import com.misim.util.TimeUtil;
import com.misim.util.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "유저 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SmsService smsService;
    private final VerificationTokenService verificationTokenService;

    // 약관 동의 -> 회원 정보 기입 -> 본인 인증
    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
    @Parameter(name = "UserDto", description = "User 회원 가입을 위한 데이터.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원 가입 성공."),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(responseCode = "409", description = "이미 가입된 유저입니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/signUp")
    public void signupUser(@RequestBody SignUpUserRequest signUpUserRequest) {

        // 유저 데이터 검사 - invalid인 경우 exception 발생
        signUpUserRequest.check();

        // 유저 정보 등록
        userService.registerUser(signUpUserRequest);
    }

    // 본인 인증
    @Operation(summary = "SMS 인증 코드 발송", description = "SMS를 통해 인증 코드를 발송합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SMS 인증 코드 발송 성공."),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/sendVerificationBySMS")
    public void sendSMSVerificationCode(@RequestBody SendSMSRequest request) {

        Validator.validatePhoneNumber(request.getPhoneNumber());

        smsService.sendSMS(request.getPhoneNumber());
    }

    @Operation(summary = "SMS 인증 코드 확인", description = "전달받은 인증 코드를 확인합니다..")
    @Parameter(name = "VerificationDto", description = "인증과 연관된 데이터.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SMS 인증 코드 확인 성공."),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/verifyAccountSMS")
    public CommonResponse<VerifySMSResponse> checkSMSVerificationCode(
        @RequestBody VerifySMSRequest request) {

        request.check();

        VerifySMSResponse response = smsService.matchSMS(request.getPhoneNumber(),
            request.getCode(),
            TimeUtil.getNow());

        return CommonResponse
            .<VerifySMSResponse>builder()
            .body(response)
            .build();
    }


    // 아이디 찾기 - 본인 인증 -> 아이디 찾기 결과
    @Operation(summary = "아이디 찾기", description = "본인 인증 후 인증 토큰을 이용하여 아이디 찾기")
    @Parameter(name = "VerificationDto", description = "인증과 연관된 데이터.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "아이디 찾기 성공."),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/nickname/find")
    public CommonResponse<FindNicknameResponse> findNickname(
        @RequestBody FindNicknameRequest request) {

        request.check();

        FindNicknameResponse response = verificationTokenService.findUserNicknameByToken(
            request.getToken());

        return CommonResponse
            .<FindNicknameResponse>builder()
            .body(response)
            .build();
    }


    // 비밀번호 리셋 - 아이디 입력 -> 본인 인증 -> 비밀번호 리셋 -> 임시 비밀번호 전달
    @Operation(summary = "비밀번호 초기화", description = "본인 인증 후 인증 토큰을 이용하여 비밀번호 초기화")
    @Parameter(name = "VerificationDto", description = "인증과 연관된 데이터.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "비밀번호 초기화 성공."),
        @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/help/resetPassword")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {

        request.check();

        userService.resetUserPassword(request.getNickname(), request.getToken());
    }


    // 비밀번호 변경 - 로그인 -> 비밀번호 변경
    @GetMapping("/help/changePassword")
    public void changePassword() {

    }
}

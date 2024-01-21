package song.mall2.domain.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.account.dto.*;
import song.mall2.domain.account.entity.ResetPasswordToken;
import song.mall2.domain.account.service.AccountService;
import song.mall2.domain.account.service.EmailService;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.user.dto.UserDto;

import java.net.URI;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final EmailService emailService;

    public static final String LOCALHOST_URL = "localhost:8080/account/resetPassword/";
    public static final String AWS_URL = "http://52.79.222.161:8080/account/resetPassword/";

    @PostMapping("/signup")
    public ResponseEntity<ResponseApi> postSaveUser(@Valid @RequestBody SignupDto signupDto) {
        UserDto user = accountService.saveUser(signupDto);

        return ResponseEntity.created(URI.create("/login")).body(new ResponseApi(true, "회원가입 성공", user));
    }

    @PostMapping("/validateUsername")
    public ResponseEntity<ResponseApi> validateUsername(@Valid @RequestBody RequestValidateUsername requestValidateUsername) {
        accountService.validateUsername(requestValidateUsername.getUsername());

        return ResponseEntity.ok(new ResponseApi(true, "중복 검증 성공", null));
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<ResponseApi> postVerifyEmail(@Valid @RequestBody RequestVerifyEmail requestVerifyEmail) {
        String token = accountService.createEmailVerificationToken(requestVerifyEmail.getEmail());

//        emailService.sendMail(requestVerifyEmail.getEmail(), "이메일 인증", "token: " + token);

        return ResponseEntity.ok(new ResponseApi(true, "인증 토큰 발송", null));
    }

    @PostMapping("/verifyEmailToken")
    public ResponseEntity<ResponseApi> postVerifyEmailToken(@RequestBody RequestVerifyEmailToken requestVerifyEmailToken) {
        accountService.verifyEmail(requestVerifyEmailToken.getEmail(), requestVerifyEmailToken.getToken());

        return ResponseEntity.ok(new ResponseApi(true, "토큰 인증 성공", null));
    }

    @PostMapping("/findUsername")
    public ResponseEntity<ResponseFindUsername> postFindUsername(@RequestBody RequestFindUsername requestFindUsername) {
        ResponseFindUsername findUsername = accountService.findUsername(requestFindUsername.getName(), requestFindUsername.getEmail());

        return ResponseEntity.ok(findUsername);
    }

    @PostMapping("/findPassword")
    public ResponseEntity<Object> postFindPassword(@RequestBody RequestFindPassword requestFindPassword) {
        ResetPasswordToken passwordToken = accountService.createResetPasswordToken(requestFindPassword.getUsername(), requestFindPassword.getName(), requestFindPassword.getEmail());

//        emailService.sendMail(passwordToken.getEmail(), "비밀번호 초기화", AWS_URL + passwordToken.getToken());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/resetPassword/{token}")
    public ResponseEntity<Object> getResetPassword(@PathVariable("token") String token) {
        accountService.validateRestPasswordToken(token);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/resetPassword/{token}")
    public ResponseEntity<Object> postResetPassword(@PathVariable("token") String token,
                                                    @RequestBody RequestResetPassword requestResetPassword) {
        accountService.resetPassword(token, requestResetPassword.getNewPassword(), requestResetPassword.getConfirmPassword());

        return ResponseEntity.ok().build();
    }
}

package song.mall2.domain.account.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.account.dto.*;
import song.mall2.domain.account.service.AccountService;
import song.mall2.domain.account.service.EmailService;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.user.dto.UserDto;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseApi<UserDto, String> postSaveUser(@Valid @RequestBody SignupDto signupDto) {
        UserDto user = accountService.saveUser(signupDto);

        return new ResponseApi<>(HttpStatus.CREATED.value(), "회원가입 성공", user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/validateUsername")
    public ResponseApi<Object, String> validateUsername(@Valid @RequestBody RequestValidateUsername requestValidateUsername) {
        accountService.validateUsername(requestValidateUsername.getUsername());

        return new ResponseApi<>(HttpStatus.OK.value(), "아이디 중복 검증 성공", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/verifyEmail")
    public ResponseApi<Object, String> postVerifyEmail(@Valid @RequestBody RequestVerifyEmail requestVerifyEmail) throws MessagingException {
        String token = accountService.createEmailVerificationToken(requestVerifyEmail.getEmail());

        emailService.sendMail(requestVerifyEmail.getEmail(), "이메일 인증", "token: " + token);

        return new ResponseApi<>(HttpStatus.OK.value(), "인증 토큰 발송", null);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/verifyEmailToken")
    public ResponseApi<Object, String> postVerifyEmailToken(@Valid @RequestBody RequestVerifyEmailToken requestVerifyEmailToken) {
        accountService.verifyEmail(requestVerifyEmailToken.getEmail(), requestVerifyEmailToken.getToken());

        return new ResponseApi<>(HttpStatus.OK.value(), "토큰 인증 성공", null);
    }
}

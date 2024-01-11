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

    @PostMapping("/signupUser")
    public ResponseEntity<Object> postSaveUser(@Valid @RequestBody UserSignupDto userSignupDto) {
        accountService.saveUser(userSignupDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signupSeller")
    public ResponseEntity<Object> postSaveSeller(@Valid @RequestBody SellerSignupDto sellerSignupDto) {
        accountService.saveSeller(sellerSignupDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validateUsername")
    public ResponseEntity<Object> validateUsername(@RequestBody RequestValidateUsername requestValidateUsername) {
        accountService.validateUsername(requestValidateUsername.getUsername());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<Object> postVerifyEmail(@RequestBody RequestVerifyEmail requestVerifyEmail) {
        String token = accountService.createEmailVerificationToken(requestVerifyEmail.getEmail());

        emailService.sendMail(requestVerifyEmail.getEmail(), "이메일 인증", "token: " + token);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/verifyEmailToken")
    public ResponseEntity<Object> postVerifyEmailToken(@RequestBody RequestVerifyEmailToken requestVerifyEmailToken) {
        accountService.verifyEmail(requestVerifyEmailToken.getEmail(), requestVerifyEmailToken.getToken());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/findUsername")
    public ResponseEntity<ResponseFindUsername> postFindUsername(@RequestBody RequestFindUsername requestFindUsername) {
        ResponseFindUsername findUsername = accountService.findUsername(requestFindUsername.getName(), requestFindUsername.getEmail());

        return ResponseEntity.ok(findUsername);
    }

    @PostMapping("/findPassword")
    public ResponseEntity<Object> postFindPassword(@RequestBody RequestFindPassword requestFindPassword) {
        ResetPasswordToken passwordToken = accountService.createResetPasswordToken(requestFindPassword.getUsername(), requestFindPassword.getName(), requestFindPassword.getEmail());

        emailService.sendMail(passwordToken.getEmail(), "비밀번호 초기화", AWS_URL + passwordToken.getToken());

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

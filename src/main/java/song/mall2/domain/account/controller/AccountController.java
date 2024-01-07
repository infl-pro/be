package song.mall2.domain.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.account.dto.*;
import song.mall2.domain.account.service.AccountService;
import song.mall2.domain.user.service.UserService;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/signupUser")
    public ResponseEntity<Object> postSaveUser(@RequestBody UserSignupDto userSignupDto) {
        userService.saveCommonUser(userSignupDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signupSeller")
    public ResponseEntity<Object> postSaveSeller(@RequestBody SellerSignupDto sellerSignupDto) {
        userService.saveSellerUser(sellerSignupDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validateUsername")
    public ResponseEntity<Object> validateUsername(@RequestBody RequestValidateUsername requestValidateUsername) {
        accountService.validateUsername(requestValidateUsername.getUsername());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<Object> postVerifyEmail(@RequestBody RequestVerifyEmail requestVerifyEmail) {
        accountService.createEmailVerificationToken(requestVerifyEmail.getEmail());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/verifyEmail/{token}")
    public ResponseEntity<Object> postVerifyEmailToken(@PathVariable("token") String token) {
        accountService.verifyEmail(token);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/findUsername")
    public ResponseEntity<ResponseFindUsername> postFindUsername(@RequestBody RequestFindUsername requestFindUsername) {
        ResponseFindUsername findUsername = accountService.findUsername(requestFindUsername.getName(), requestFindUsername.getEmail());

        return ResponseEntity.ok(findUsername);
    }

    @PostMapping("/findPassword")
    public ResponseEntity<Object> postFindPassword(@RequestBody RequestFindPassword requestFindPassword) {
        accountService.createResetPasswordToken(requestFindPassword.getUsername(), requestFindPassword.getName(), requestFindPassword.getEmail());

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

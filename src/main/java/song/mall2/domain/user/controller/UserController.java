package song.mall2.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.user.dto.RequestUpdatePassword;
import song.mall2.domain.user.dto.RequestUpdateProfile;
import song.mall2.domain.user.dto.UserDto;
import song.mall2.domain.user.service.UserService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserDto user = userService.getUser(userPrincipal.getId());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<UserDto> postUpdateProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                     @RequestBody RequestUpdateProfile requestUpdateProfile) {
        UserDto user = userService.updateProfile(userPrincipal.getId(), requestUpdateProfile.getProfileUrl());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<UserDto> postUpdatePassword(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                      @RequestBody RequestUpdatePassword requestUpdatePassword) {
        UserDto user = userService.updatePassword(userPrincipal.getId(), requestUpdatePassword.getOriginalPassword(), requestUpdatePassword.getNewPassword(), requestUpdatePassword.getConfirmNewPassword());

        return ResponseEntity.ok(user);
    }
}

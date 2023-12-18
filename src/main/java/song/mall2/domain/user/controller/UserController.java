package song.mall2.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.user.dto.SignupDto;
import song.mall2.domain.user.service.UserService;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save")
    public void postSave(@RequestBody SignupDto signupDto) {
        userService.save(signupDto);
    }
}

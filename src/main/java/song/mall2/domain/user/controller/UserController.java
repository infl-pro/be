package song.mall2.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.user.dto.SellerSignupDto;
import song.mall2.domain.user.dto.UserSignupDto;
import song.mall2.domain.user.service.UserService;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signupUser")
    public ResponseEntity<Object> postSaveUser(@RequestBody UserSignupDto userSignupDto) {
        userService.saveCommonUser(userSignupDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("signupSeller")
    public ResponseEntity<Object> postSaveSeller(@RequestBody SellerSignupDto sellerSignupDto) {
        userService.saveSellerUser(sellerSignupDto);

        return ResponseEntity.ok().build();
    }
}

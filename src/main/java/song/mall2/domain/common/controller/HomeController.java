package song.mall2.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import song.mall2.domain.common.dto.AccessTokenResponseDto;
import song.mall2.domain.common.dto.RefreshRequestDto;
import song.mall2.security.utils.jwt.JwtUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String getHome() {

        return "home";
    }

    @PostMapping("/")
    public String postHome() {

        return "home";
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AccessTokenResponseDto> postRefreshToken(@RequestBody RefreshRequestDto refreshRequestDto) {
        String username = JwtUtils.validateRefreshToken(refreshRequestDto.getRefreshToken());
        String jwt = JwtUtils.createJwt(username);

        return ResponseEntity.ok(new AccessTokenResponseDto(jwt));
    }
}

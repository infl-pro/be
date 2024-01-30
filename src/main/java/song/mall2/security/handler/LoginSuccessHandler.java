package song.mall2.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.jwt.service.JwtService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;
import song.mall2.security.cors.CorsFactory;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        HttpServletResponse httpServletResponse = CorsFactory.setCors(request, response);

        JwtService.TokenDto tokenDto = jwtService.createJwt(userPrincipal);

        Cookie refreshToken = new Cookie("refreshToken", tokenDto.getRefreshToken());
        refreshToken.setHttpOnly(true);
        refreshToken.setMaxAge(10 * 60 * 60);
        httpServletResponse.addCookie(refreshToken);

        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new ResponseApi<>(response.getStatus(), "인증 성공", tokenDto.getAccessToken())));
    }
}

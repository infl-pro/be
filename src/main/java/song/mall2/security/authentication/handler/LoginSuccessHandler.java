package song.mall2.security.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import song.mall2.security.authentication.userprincipal.UserPrincipal;
import song.mall2.security.utils.jwt.JwtUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        Long userId = userPrincipal.getId();
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", JwtUtils.createJwt(userId, username, authorities));
        tokens.put("refreshToken", JwtUtils.createRefreshToken(userId, username, authorities));

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(tokens));
    }
}

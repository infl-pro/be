package song.mall2.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import song.mall2.domain.common.api.ResponseApi;
import song.mall2.domain.jwt.service.JwtService;
import song.mall2.exception.invalid.exceptions.InvalidJwtException;
import song.mall2.security.authentication.userprincipal.UserPrincipal;
import song.mall2.security.authentication.userprincipal.service.UserDetailsServiceImpl;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorization.substring(7);

            JwtParser parser = JwtService.getAccessTokenParser();
            Jws<Claims> jws = null;
            try {
                jws = parser.parseSignedClaims(token);
            } catch (ExpiredJwtException e) {
                throw new InvalidJwtException("토큰이 만료 되었습니다.");
            }

            UserPrincipal userPrincipal = getUserPrincipal(jws);

            UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(userPrincipal, null, userPrincipal.getAuthorities());
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);
        } catch (InvalidJwtException e) {
            doResponse(response, HttpStatus.UNAUTHORIZED.value(), e, "토큰이 만료되었습니다.");
            return;
        } catch (JwtException e) {
            doResponse(response, HttpStatus.BAD_REQUEST.value(), e, "유효하지 않은 인증 토큰입니다.");
            return;
        } catch (Exception e) {
            doResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), e, "알 수 없는 에러가 발생했습니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private UserPrincipal getUserPrincipal(Jws<Claims> jws) {
        Long userId = Long.valueOf(jws.getPayload().getSubject());
        String username = String.valueOf(jws.getPayload().get("username"));
        List<?> authorities = ((List<?>) jws.getPayload().get("roles"));

        return UserPrincipal.create(userId, username, authorities);
    }

    private void doResponse(HttpServletResponse response, int status, Exception e, String message) throws IOException {
        SecurityContextHolder.clearContext();

        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(new ResponseApi<>(response.getStatus(), e.getClass(), message)));
    }
}

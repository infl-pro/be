package song.mall2.security.filter;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import song.mall2.domain.jwt.service.JwtService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

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
            Jws<Claims> jws = parser.parseSignedClaims(token);
            UserPrincipal userPrincipal = getUserPrincipal(jws);

            UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(userPrincipal, null, userPrincipal.getAuthorities());
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
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
}

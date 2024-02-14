package song.mall2.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class LogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress.indexOf(':') >= 0) {
            ipAddress = ipAddress.split(":")[3];
        }

        String name = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            name = authentication.getName();
        }

        String origin = request.getHeader("Origin");
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        log.info("[Log Filter] ipAddress = {}, origin = {}, name = {}, uri = {}, method = {}", ipAddress, origin, name, requestURI, method);

        Cookie[] cookies = request.getCookies();
        log.info("cookie check");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("cookie name = {} value = {}", cookie.getName(), cookie.getValue());
            }
        }

        filterChain.doFilter(request, response);
    }
}

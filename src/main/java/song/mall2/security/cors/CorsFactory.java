package song.mall2.security.cors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CorsFactory {

    public static HttpServletResponse setCors(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Method", "GET, POST, DELETE, OPTIONS, PUT, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, refreshToken");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        return response;
    }
}

package song.mall2.security.utils.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import song.mall2.exception.invalid.exceptions.InvalidJwtException;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class JwtUtils {
    private final static SecretKey KEY = Jwts.SIG.HS256.key().build();
    private final static SecretKey REFRESH_KEY = Jwts.SIG.HS512.key().build();

    public static String createJwt(Long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plus(Duration.ofMillis(36000000));

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", authorities);


        return Jwts.builder()
                .issuer("TIss")
                .subject(userId.toString())
                .expiration(Timestamp.valueOf(expiryTime))
                .issuedAt(Timestamp.valueOf(now))
                .claims(claims)
                .signWith(KEY)
                .compact();
    }

    public static String createRefreshToken(Long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plus(Duration.ofMillis(259200000));

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", authorities);

        return Jwts.builder()
                .issuer("TIss")
                .subject(userId.toString())
                .expiration(Timestamp.valueOf(expiryTime))
                .issuedAt(Timestamp.valueOf(now))
                .claims(claims)
                .signWith(REFRESH_KEY)
                .compact();
    }

    public static String validateJwt(String jwt) {
        JwtParser parser = Jwts.parser().verifyWith(KEY).build();

        Jws<Claims> jws = null;
        try {
            jws = parser.parseSignedClaims(jwt);
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtException("토큰이 만료 되었습니다.");
        }

        return jws.getPayload().get("username").toString();
    }

    public static String refreshJwt(String refreshToken) throws JwtException {
        JwtParser parser = Jwts.parser().verifyWith(REFRESH_KEY).build();

        Jws<Claims> jws = null;
        try {
            jws = parser.parseSignedClaims(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtException("토큰이 만료 되었습니다.");
        }

        Claims payload = jws.getPayload();

        Long userId = Long.valueOf(payload.getSubject());
        String username = payload.get("username").toString();
        Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) payload.get("roles");
        String jwt = createJwt(userId, username, authorities);

        return jwt;
    }
}

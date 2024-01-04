package song.mall2.security.utils.jwt;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class JwtUtils {
    private final static SecretKey KEY = Jwts.SIG.HS256.key().build();
    private final static SecretKey REFRESH_KEY = Jwts.SIG.HS512.key().build();

    public static String createJwt(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plus(Duration.ofMillis(36000000));

        return Jwts.builder()
                .issuer("TIss")
                .subject(username)
                .expiration(Timestamp.valueOf(expiryTime))
                .issuedAt(Timestamp.valueOf(now))
                .signWith(KEY)
                .compact();
    }

    public static String createRefreshToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plus(Duration.ofMillis(259200000));

        return Jwts.builder()
                .issuer("TIss")
                .subject(username)
                .expiration(Timestamp.valueOf(expiryTime))
                .issuedAt(Timestamp.valueOf(now))
                .signWith(REFRESH_KEY)
                .compact();
    }

    public static String validateJwt(String jwt) throws JwtException {
        JwtParser parser = Jwts.parser().verifyWith(KEY).build();

        Jws<Claims> jws = parser.parseSignedClaims(jwt);

        Date expiration = jws.getPayload().getExpiration();
        if (expiration.before(new Date())) {
            throw new JwtException("시간 만료");
        }

        return jws.getPayload().getSubject();
    }

    public static String validateRefreshToken(String refreshToken) throws JwtException {
        JwtParser parser = Jwts.parser().verifyWith(REFRESH_KEY).build();

        Jws<Claims> jws = parser.parseSignedClaims(refreshToken);

        Date expiration = jws.getPayload().getExpiration();
        if (expiration.before(new Date())) {
            throw new JwtException("시간 만료");
        }

        return jws.getPayload().getSubject();
    }
}

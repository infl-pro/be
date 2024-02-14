package song.mall2.domain.jwt.service;

import io.jsonwebtoken.*;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import song.mall2.exception.invalid.exceptions.InvalidJwtException;
import song.mall2.security.authentication.userprincipal.UserPrincipal;
import song.mall2.security.authentication.userprincipal.service.UserDetailsServiceImpl;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserDetailsServiceImpl userDetailsService;

    private final static SecretKey KEY = Jwts.SIG.HS256.key().build();
    private final static SecretKey REFRESH_KEY = Jwts.SIG.HS512.key().build();

    public static JwtParser getAccessTokenParser() {
        return Jwts.parser().verifyWith(KEY).build();
    }

    public static JwtParser getRefreshTokenParser() {
        return Jwts.parser().verifyWith(REFRESH_KEY).build();
    }

    @Transactional
    public TokenDto createJwt(UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> claims = getClaims(userPrincipal.getUsername(), userPrincipal.getAuthorities());

        String accessToken = createAccessToken(userId, now, claims);
        String refreshToken = createRefreshToken(userPrincipal.getUsername(), now);

        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public TokenDto.AccessTokenDto reissueToken(String refreshToken) {
        try {
            Jws<Claims> jws = getRefreshTokenParser().parseSignedClaims(refreshToken);
            Claims payload = jws.getPayload();
            String username = payload.getSubject();

            UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(username);
            LocalDateTime now = LocalDateTime.now();
            Map<String, Object> claims = getClaims(userPrincipal.getUsername(), userPrincipal.getAuthorities());
            String accessToken = createAccessToken(userPrincipal.getId(), now, claims);

            return new TokenDto(accessToken, refreshToken).getAccessToken();
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtException("이미 만료된 인증 토큰입니다.");
        } catch (Exception e) {
            throw e;
        }
    }

    private Map<String, Object> getClaims(String username, Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", authorities);
        return claims;
    }

    private String createAccessToken(Long userId, LocalDateTime now, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(userId.toString())
                .expiration(Timestamp.valueOf(now.plus(Duration.ofMillis(1800000))))
                .issuedAt(Timestamp.valueOf(now))
                .claims(claims)
                .signWith(KEY)
                .compact();
    }

    private String createRefreshToken(String username, LocalDateTime now) {
        return Jwts.builder()
                .subject(username)
//                .expiration(Timestamp.valueOf(now.plus(Duration.ofMillis(36000000))))
                .expiration(Timestamp.valueOf(now.plus(Duration.ofMillis(1))))
                .issuedAt(Timestamp.valueOf(now))
                .signWith(REFRESH_KEY)
                .compact();
    }

    @Getter @Service
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenDto {
        private AccessTokenDto accessToken;
        private String refreshToken;

        public TokenDto(String accessToken, String refreshToken) {
            this.accessToken = new AccessTokenDto(accessToken, "Bearer");
            this.refreshToken = refreshToken;
        }

        @Getter @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AccessTokenDto {
            private String accessToken;
            private String tokenType;
        }
    }
}

package song.mall2.domain.jwt.service;

import io.jsonwebtoken.*;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import song.mall2.domain.jwt.entity.JwtEntity;
import song.mall2.domain.jwt.repository.JwtJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.invalid.exceptions.InvalidJwtException;
import song.mall2.exception.notfound.exceptions.RefreshTokenNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtJpaRepository jwtRepository;
    private final UserJpaRepository userRepository;

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
        String refreshToken = createRefreshToken(now);

        Optional<JwtEntity> optionalJwt = jwtRepository.findByUserId(userId);
        if (optionalJwt.isPresent()) {
            JwtEntity jwt = optionalJwt.get();

            jwt.updateJwt(refreshToken);
            JwtEntity saveJwt = jwtRepository.save(jwt);

            return new TokenDto(accessToken, saveJwt.getRefreshToken());
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        JwtEntity jwt = new JwtEntity(user, refreshToken);
        JwtEntity saveJwt = jwtRepository.save(jwt);

        return new TokenDto(accessToken, saveJwt.getRefreshToken());
    }

    @Transactional
    public TokenDto reissueToken(String accessToken, String refreshToken) {
        if (!isTokenExpired(getAccessTokenParser(), accessToken)) {
            
        }

        JwtEntity jwt = jwtRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException("토큰을 찾을 수 없습니다."));

        User user = userRepository.findById(jwt.getId())
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        if (isTokenExpired(getRefreshTokenParser(), jwt.getRefreshToken())) {
            throw new InvalidJwtException("토큰이 만료되었습니다.");
        }

        Map<String, Object> claims = getClaims(user.getUsername(), user.getRoleList().stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name())).toList());
        String newAccessToken = createAccessToken(user.getId(), LocalDateTime.now(), claims);
        String newRefreshToken = createRefreshToken(LocalDateTime.now());

        jwt.updateJwt(newRefreshToken);
        JwtEntity saveJwt = jwtRepository.save(jwt);

        return new TokenDto(newAccessToken, saveJwt.getRefreshToken());
    }

    private Map<String, Object> getClaims(String username, Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", authorities);
        return claims;
    }

    private String createAccessToken(Long userId, LocalDateTime now, Map<String, Object> claims) {
        String accessToken = Jwts.builder()
                .subject(userId.toString())
                .expiration(Timestamp.valueOf(now.plus(Duration.ofMillis(1800000))))
                .issuedAt(Timestamp.valueOf(now))
                .claims(claims)
                .signWith(KEY)
                .compact();
        return accessToken;
    }

    private String createRefreshToken(LocalDateTime now) {
        String refreshToken = Jwts.builder()
                .subject(UUID.randomUUID().toString().substring(0, 8))
                .expiration(Timestamp.valueOf(now.plus(Duration.ofMillis(7200000))))
                .issuedAt(Timestamp.valueOf(now))
                .signWith(REFRESH_KEY)
                .compact();
        return refreshToken;
    }

    private boolean isTokenExpired(JwtParser parser, String refreshToken) {
        try {
            Claims payload = parser.parseSignedClaims(refreshToken)
                    .getPayload();

            return payload.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            throw e;
        }
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

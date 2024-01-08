package song.mall2.domain.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerificationToken {
    @Id @GeneratedValue
    private Long id;

    private String email;
    private String token;
    private boolean isVerified;
    private LocalDateTime expiryTime;

    public EmailVerificationToken(String email, String token) {
        this.email = email;
        this.token = token;
        this.isVerified = false;
        expiryTime = LocalDateTime.now().plusMinutes(5);
    }

    public void updateToken(String token) {
        this.token = token;
        expiryTime = LocalDateTime.now().plusMinutes(5);
    }

    public void verifyEmail() {
        this.isVerified = true;
    }
}

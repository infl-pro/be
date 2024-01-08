package song.mall2.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.mall2.domain.account.entity.EmailVerificationToken;

import java.util.Optional;

@Repository
public interface EmailTokenJpaRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByEmail(String email);

    Optional<EmailVerificationToken> findByEmailAndToken(String email, String token);
}

package song.mall2.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.mall2.domain.account.entity.ResetPasswordToken;

import java.util.Optional;

@Repository
public interface ResetPasswordTokenJpaRepository extends JpaRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByToken(String token);

    Optional<ResetPasswordToken> findByEmail(String email);
}

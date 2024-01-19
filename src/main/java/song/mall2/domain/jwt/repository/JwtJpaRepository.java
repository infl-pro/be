package song.mall2.domain.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.mall2.domain.jwt.entity.JwtEntity;

import java.util.Optional;

@Repository
public interface JwtJpaRepository extends JpaRepository<JwtEntity, Long> {
    Optional<JwtEntity> findByUserId(Long userId);

    Optional<JwtEntity> findByRefreshToken(String refreshToken);
}

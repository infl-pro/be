package song.mall2.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.user.entity.UserRole;
import song.mall2.domain.user.entity.UserRole.Role;

import java.util.Optional;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long> {
    @Query("select ur from UserRole ur where ur.user.id = :userId and ur.role = :role")
    Optional<UserRole> findByUserIdAndRole(@Param("userId") Long userId,
                                           @Param("role") Role role);
}

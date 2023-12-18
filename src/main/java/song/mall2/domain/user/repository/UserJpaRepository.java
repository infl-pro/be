package song.mall2.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.mall2.domain.user.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

}

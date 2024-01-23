package song.mall2.domain.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.favorite.entity.Favorite;

import java.util.Optional;

@Repository
public interface FavoriteJpaRepository extends JpaRepository<Favorite, Long> {
    @Query("select f from Favorite f where f.user.id = :userId and f.product.id = :productId")
    Optional<Favorite> findByUserIdAndProductId(@Param("userId") Long userId,
                                                @Param("productId") Long productId);
}

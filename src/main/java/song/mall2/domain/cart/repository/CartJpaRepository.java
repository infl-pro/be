package song.mall2.domain.cart.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.cart.entity.Cart;

import java.util.List;

@Repository
public interface CartJpaRepository extends JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = "product")
    @Query("select c from Cart c where c.user.id = :userId")
    List<Cart> findAllByUserId(@Param("userId") Long userId);
}

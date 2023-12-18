package song.mall2.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.mall2.domain.cart.entity.Cart;

@Repository
public interface CartJpaRepository extends JpaRepository<Cart, Long> {

}

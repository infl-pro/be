package song.mall2.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.mall2.domain.order.entity.OrderProduct;

@Repository
public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {

}

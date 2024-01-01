package song.mall2.domain.order.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.order.entity.OrderProduct;

import java.util.List;

@Repository
public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {
    @EntityGraph(attributePaths = {"orders", "product"})
    @Query("select op from OrderProduct op where op.orders.id = :ordersId")
    List<OrderProduct> findAllByOrdersId(@Param("ordersId") Long ordersId);

    @EntityGraph(attributePaths = {"orders", "product"})
    @Query("select op from OrderProduct op where op.user.id = :userId")
    List<OrderProduct> findAllByUserId(@Param("userId") Long userId);
}

package song.mall2.domain.orderproduct.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.order.entity.Orders;

import java.util.Optional;

@Repository
public interface OrdersJpaRepository extends JpaRepository<Orders, Long> {
    @EntityGraph(attributePaths = {"user", "payment"})
    @Query("select o from Orders o where o.id = :ordersId and o.user.id = :userId")
    Optional<Orders> findByIdAndUserId(@Param("ordersId") Long ordersId,@Param("userId") Long userId);

    @Query("select o from Orders o where o.payment.paymentId = :paymentId")
    Optional<Orders> findByPaymentId(@Param("paymentId") String paymentId);
}

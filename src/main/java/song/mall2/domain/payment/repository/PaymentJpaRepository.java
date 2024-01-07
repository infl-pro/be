package song.mall2.domain.payment.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.payment.entity.Payment;

import java.util.Optional;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
    @EntityGraph(attributePaths = {"user", "orders"})
    @Query("select p from Payment p where p.paymentId = :paymentId")
    Optional<Payment> findByPaymentId(@Param("paymentId") String paymentId);

    @EntityGraph(attributePaths = {"user", "orders"})
    @Query("select p from Payment p where p.orders.id = :ordersId")
    Optional<Payment> findByOrdersId(@Param("ordersId") Long ordersId);
}

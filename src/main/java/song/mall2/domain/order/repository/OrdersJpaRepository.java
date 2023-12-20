package song.mall2.domain.order.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.order.entity.Orders;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersJpaRepository extends JpaRepository<Orders, Long> {
    @EntityGraph(attributePaths = "user")
    Optional<Orders> findById(Long ordersId);

    @Query("select o from Orders o where o.user.id = :userId")
    List<Orders> findAllByUserId(@Param("userId") Long userId);
}

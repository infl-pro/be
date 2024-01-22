package song.mall2.domain.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.orderproduct.entity.OrderProduct;
import song.mall2.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {
    @EntityGraph(attributePaths = {"orders", "product", "user"})
    @Query("select op from OrderProduct op where op.id = :id")
    Optional<OrderProduct> findById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"orders", "product"})
    @Query("select op from OrderProduct op where op.orders.id = :ordersId")
    List<OrderProduct> findAllByOrdersId(@Param("ordersId") Long ordersId);

    @EntityGraph(attributePaths = {"orders", "product"})
    @Query("select op from OrderProduct op where op.user.id = :userId")
    List<OrderProduct> findAllByUserId(@Param("userId") Long userId);

    @Query("select op from OrderProduct op where op.product.id = :productId and op.user.id = :userId")
    List<OrderProduct> findAllByProductIdAndUserId(@Param("productId") Long productId,
                                                   @Param("userId") Long userId);

    @EntityGraph(attributePaths = {"orders", "orders.payment"})
    @Query("select op from OrderProduct op where op.product.id = :productId")
    Page<OrderProduct> findAllByProductId(@Param("productId") Long productId,
                                          Pageable pageable);
}

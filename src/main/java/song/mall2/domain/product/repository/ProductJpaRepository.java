package song.mall2.domain.product.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.product.entity.Product;

import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = {"user"})
    @Query("select p from Product p where p.id = :productId")
    Optional<Product> findById(@Param("productId") Long productId);
}

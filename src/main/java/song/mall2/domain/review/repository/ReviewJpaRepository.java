package song.mall2.domain.review.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.mall2.domain.review.entity.Review;

import java.util.List;

@Repository
public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    @EntityGraph(attributePaths = {"user"})
    @Query("select r from Review r where r.orderProduct.product.id = :productId")
    List<Review> findAllByProductId(@Param("productId") Long productId);
}

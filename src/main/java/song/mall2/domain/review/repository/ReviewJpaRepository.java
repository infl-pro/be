package song.mall2.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.mall2.domain.review.entity.Review;

@Repository
public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

}

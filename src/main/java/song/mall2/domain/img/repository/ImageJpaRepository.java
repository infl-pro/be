package song.mall2.domain.img.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.mall2.domain.img.entity.Image;

import java.util.List;

@Repository
public interface ImageJpaRepository extends JpaRepository<Image, Long> {

    Integer deleteAllByProductId(Long productId);

    List<Image> findAllByProductId(Long productId);
}

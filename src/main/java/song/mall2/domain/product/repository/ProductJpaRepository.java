package song.mall2.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.mall2.domain.product.entity.Product;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

}

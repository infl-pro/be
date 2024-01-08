package song.mall2.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select p from Product p where p.id = :productId and p.user.id = :userId")
    Optional<Product> findByIdAndUserId(@Param("productId") Long productId,
                                        @Param("userId") Long userId);

    @EntityGraph(attributePaths = {"user"})
    @Query("select p from Product p")
    Page<Product> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("select p from Product p where p.name like %:searchValue%")
    Page<Product> findAllBySearch(Pageable pageable,
                                  @Param("searchValue") String searchValue);

    @EntityGraph(attributePaths = {"user"})
    @Query("select p from Product p where p.category = :category")
    Page<Product> findAllByCategory(Pageable pageable,
                                    @Param("category") Product.Category category);

    @Query("select p from Product p where p.name like %:searchValue% and p.category = :category")
    Page<Product> findAllBySearchAndCategory(Pageable pageable,
                                                    @Param("searchValue") String searchValue,
                                                    @Param("category") Product.Category category);
}

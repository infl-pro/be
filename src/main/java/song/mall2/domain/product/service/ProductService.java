package song.mall2.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.product.dto.ProductDto;
import song.mall2.domain.product.dto.ProductIdDto;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJpaRepository productJpaRepository;
    private final UserJpaRepository userRepository;

    @Transactional
    public ProductIdDto saveProduct(Long userId, SaveProductDto saveProductDto) {
        User user = getUserById(userId);
        Product product = Product.create(user, saveProductDto.getName(), saveProductDto.getPrice(), saveProductDto.getDescription(), saveProductDto.getStockQuantity());

        Product saveProduct = productJpaRepository.save(product);

        return new ProductIdDto(saveProduct.getId());
    }

    public ProductDto getProduct(Long productId) {
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getStockQuantity(),
                product.getUser().getId(), product.getUser().getUsername());
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}

package song.mall2.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.order.entity.OrderProduct;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.product.dto.EditProductDto;
import song.mall2.domain.product.dto.ProductDto;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJpaRepository productRepository;
    private final UserJpaRepository userRepository;
    private final OrderProductJpaRepository orderProductRepository;

    @Transactional
    public ProductDto saveProduct(Long userId, SaveProductDto saveProductDto) {
        User user = getUserById(userId);
        Product product = Product.create(user, saveProductDto.getName(), saveProductDto.getPrice(), saveProductDto.getDescription(),
                saveProductDto.getThumbnailUrl(), saveProductDto.getProductImgUrl(), saveProductDto.getStockQuantity(), saveProductDto.getCategoryName());

        Product saveProduct = productRepository.save(product);

        return new ProductDto(saveProduct.getId(), product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getImgUrl(), product.getStockQuantity(),
                product.getUser().getUsername());
    }

    @Transactional
    public ProductDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getImgUrl(), product.getStockQuantity(),
                product.getUser().getUsername());
    }

    @Transactional
    public ProductDto getProduct(Long productId, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getImgUrl(), product.getStockQuantity(),
                product.getUser().getUsername());

        List<OrderProduct> orderProductList = orderProductRepository.findAllByProductIdAndUserId(product.getId(), userId);
        if (hasPurchased(orderProductList)) {
            productDto.setPurchased(true);
        }

        return productDto;
    }

    @Transactional
    public EditProductDto getEditForm(Long productId, Long userId) {
        Product product = productRepository.findByIdAndUserId(productId, userId)
                .orElseThrow(ProductNotFoundException::new);

        return new EditProductDto(product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getImgUrl(), product.getStockQuantity(), product.getCategory().name());
    }

    @Transactional
    public ProductDto editProduct(Long productId, Long userId, EditProductDto editProductDto) {
        Product product = productRepository.findByIdAndUserId(productId, userId)
                .orElseThrow(ProductNotFoundException::new);

        product.update(editProductDto.getName(), editProductDto.getPrice(), editProductDto.getDescription(),
                editProductDto.getThumbnailUrl(), editProductDto.getImgUrl(), editProductDto.getStockQuantity(), product.getCategory().name());

        Product saveProduct = productRepository.save(product);

        return new ProductDto(saveProduct.getId(), saveProduct.getName(), saveProduct.getPrice(), saveProduct.getDescription(),
                saveProduct.getThumbnailUrl(), saveProduct.getImgUrl(), saveProduct.getStockQuantity(),
                saveProduct.getUser().getUsername());
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private boolean hasPurchased(List<OrderProduct> orderProductList) {
        return !orderProductList.isEmpty() &&
                orderProductList.stream()
                        .noneMatch(orderProduct -> OrderProduct.Status.CANCELLED.equals(orderProduct.getStatus()));
    }
}

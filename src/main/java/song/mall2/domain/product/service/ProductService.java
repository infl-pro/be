package song.mall2.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import song.mall2.domain.common.dto.PageDto;
import song.mall2.domain.product.dto.*;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.review.repository.ReviewJpaRepository;
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
    private final ReviewJpaRepository reviewRepository;

    @Transactional
    public ProductDto saveProduct(Long userId, SaveProductDto saveProductDto) {
        User user = getUser(userId);
        Product product = Product.create(user, saveProductDto.getName(), saveProductDto.getPrice(), saveProductDto.getDescription(),
                saveProductDto.getThumbnailUrl(), saveProductDto.getStockQuantity(), saveProductDto.getCategoryName());

        Product saveProduct = productRepository.save(product);

        return new ProductDto(saveProduct.getId(), product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getStockQuantity(), product.getCategory().name(),
                product.getUser().getUsername(), null, false, true);
    }

    @Transactional
    public ProductDto getProduct(Long productId) {
        Product product = findById(productId);

        List<ProductReviewDto> reviewList = getReviewList(product);

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getStockQuantity(), product.getCategory().name(),
                product.getUser().getUsername(), reviewList);
    }

    @Transactional
    public ProductDto getProduct(Long productId, Long userId) {
        Product product = findById(productId);
        User user = getUser(userId);

        List<ProductReviewDto> reviewList = getReviewList(product);

        boolean isPurchased = isPurchased(user.getId(), product);
        boolean isSeller = product.isSeller(user.getId());

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getStockQuantity(), product.getCategory().name(),
                product.getUser().getUsername(), reviewList, isPurchased, isSeller);
    }

//    @Transactional
//    public EditProductDto getEditForm(Long productId, Long userId) {
//        Product product = productRepository.findByIdAndUserId(productId, userId)
//                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
//
//        return new EditProductDto(product.getName(), product.getPrice(), product.getDescription(),
//                product.getThumbnailUrl(), product.getCategory().name());
//    }

    @Transactional
    public ProductDto editProduct(Long productId, Long userId, EditProductDto editProductDto) {
        Product product = findByIdAndUserId(productId, userId);

        product.update(editProductDto.getProductName(), editProductDto.getProductPrice(), editProductDto.getProductDescription(),
                editProductDto.getStockQuantity(), editProductDto.getThumbnailUrl(), product.getCategory().name());

        Product saveProduct = productRepository.save(product);
        boolean isPurchased = isPurchased(product.getUser().getId(), product);
        List<ProductReviewDto> reviewList = getReviewList(saveProduct);

        return new ProductDto(saveProduct.getId(), saveProduct.getName(), saveProduct.getPrice(), saveProduct.getDescription(),
                saveProduct.getThumbnailUrl(), saveProduct.getStockQuantity(), saveProduct.getCategory().name(),
                saveProduct.getUser().getUsername(), reviewList, isPurchased, true);
    }

//    @Transactional
//    public ProductDto updateStockQuantity(Long productId, Long userId, Integer stockQuantity) {
//        Product product = findByIdAndUserId(productId, userId);
//
//        product.updateStockQuantity(stockQuantity);
//
//        Product saveProduct = productRepository.save(product);
//
//        List<ProductReviewDto> reviewList = getReviewList(saveProduct);
//
//        return new ProductDto(saveProduct.getId(), saveProduct.getName(), saveProduct.getPrice(), saveProduct.getDescription(),
//                saveProduct.getThumbnailUrl(), saveProduct.getStockQuantity(), saveProduct.getCategory().name(),
//                saveProduct.getUser().getUsername(), reviewList);
//    }

    @Transactional
    public PageDto findProductList(Pageable pageable) {
        Page<ProductListDto> pageDto = productRepository.findAll(pageable)
                .map(product -> new ProductListDto(
                        product.getId(), product.getName(), product.getPrice(), product.getThumbnailUrl(),
                        product.getUser().getName()
                ));


        return new PageDto(pageDto);
    }

    @Transactional
    public PageDto findProductListBySearch(Pageable pageable, String searchValue) {
        Page<ProductListDto> pageDto = productRepository.findAllBySearch(pageable, searchValue)
                .map(product -> new ProductListDto(
                        product.getId(), product.getName(), product.getPrice(), product.getThumbnailUrl(),
                        product.getUser().getName()
                ));
        return new PageDto(pageDto);
    }

    @Transactional
    public PageDto findProductListByCategory(Pageable pageable, String categoryName) {
        Product.Category category = Product.Category.of(categoryName);
        Page<ProductListDto> pageDto = productRepository.findAllByCategory(pageable, category)
                .map(product -> new ProductListDto(
                        product.getId(), product.getName(), product.getPrice(), product.getThumbnailUrl(),
                        product.getUser().getName()
                ));
        return new PageDto(pageDto);
    }

    @Transactional
    public PageDto findProductListBySearchAndCategory(Pageable pageable, String searchValue, String categoryName) {
        Product.Category category = Product.Category.of(categoryName);

        Page<ProductListDto> pageDto = productRepository.findAllBySearchAndCategory(pageable, searchValue, category)
                .map(product -> new ProductListDto(
                        product.getId(), product.getName(), product.getPrice(), product.getThumbnailUrl(),
                        product.getUser().getName()
                ));
        return new PageDto(pageDto);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
    }

    private List<ProductReviewDto> getReviewList(Product product) {
        return reviewRepository.findAllByProductId(product.getId())
                .stream()
                .map(review -> new ProductReviewDto(review.getId(), review.getUser().getUsername(), review.getContent(),
                        review.getRating(), review.getCreateAt()))
                .toList();
    }

    private Product findByIdAndUserId(Long productId, Long userId) {
        return productRepository.findByIdAndUserId(productId, userId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
    }

    private boolean isPurchased(Long userId, Product product) {
        return orderProductRepository.findAllByProductIdAndUserId(product.getId(), userId).stream()
                .anyMatch(orderProduct -> orderProduct.getProduct().getId().equals(product.getId()));
    }
}

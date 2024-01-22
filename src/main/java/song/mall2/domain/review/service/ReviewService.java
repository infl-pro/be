package song.mall2.domain.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.orderproduct.entity.OrderProduct;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.review.dto.ReviewDto;
import song.mall2.domain.review.dto.SaveReviewDto;
import song.mall2.domain.review.entity.Review;
import song.mall2.domain.review.repository.ReviewJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.invalid.exceptions.InvalidUserException;
import song.mall2.exception.notfound.exceptions.OrderProductNotFoundException;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserJpaRepository userRepository;
    private final ProductJpaRepository productRepository;
    private final OrderProductJpaRepository orderProductRepository;
    private final ReviewJpaRepository reviewRepository;

    @Transactional
    public ReviewDto saveReview(Long orderProductId, Long userId, SaveReviewDto saveReviewDto) {
        User user = getUser(userId);
        OrderProduct orderProduct = getOrderProduct(orderProductId);

        if (!orderProduct.isBuyer(user.getId())) {
            throw new InvalidUserException("접근 권한이 없습니다.");
        }

        Review review = Review.create(user, orderProduct, saveReviewDto.getContent(), saveReviewDto.getRating());
        Review saveReview = reviewRepository.save(review);

        return new ReviewDto(saveReview.getId(), saveReview.getUser().getUsername(), saveReview.getOrderProduct().getId(),
                saveReview.getOrderProduct().getProduct().getId(), saveReview.getContent(), saveReview.getRating(), saveReview.getCreateAt());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private OrderProduct getOrderProduct(Long orderProductId) {
        return orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new OrderProductNotFoundException("주문 상품을 찾을 수 없습니다."));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
    }
}

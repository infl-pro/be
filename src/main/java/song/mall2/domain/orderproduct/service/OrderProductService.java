package song.mall2.domain.orderproduct.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import song.mall2.domain.common.dto.PageDto;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.orderproduct.dto.OrderProductPaymentDto;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.invalid.exceptions.InvalidUserException;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final UserJpaRepository userRepository;
    private final ProductJpaRepository productRepository;
    private final OrderProductJpaRepository orderProductRepository;

    @Transactional
    public PageDto getOrderProductList(Long productId, Long userId, Pageable pageable) {
        User user = getUser(userId);
        Product product = getProduct(productId);

        if (!product.isSeller(user.getId())) {
            throw new InvalidUserException("접근 권한이 없습니다.");
        }

        Page<OrderProductPaymentDto> orderProductPaymentDto = orderProductRepository.findAllByProductId(productId, pageable)
                .map(orderProduct -> new OrderProductPaymentDto(orderProduct.getId(), orderProduct.getUser().getId(), orderProduct.getOrders().getId(),
                        orderProduct.getOrders().getPayment().getPaymentId(), orderProduct.getStatus().name(), orderProduct.getAmount()));

        return new PageDto(orderProductPaymentDto);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
    }
}

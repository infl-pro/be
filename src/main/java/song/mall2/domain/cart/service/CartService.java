package song.mall2.domain.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.cart.entity.Cart;
import song.mall2.domain.cart.repository.CartJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final UserJpaRepository userRepository;
    private final ProductJpaRepository productRepository;
    private final CartJpaRepository cartRepository;

    @Transactional
    public void addCart(Long userId, Long productId, Integer quantity) {
        User user = getUserById(userId);
        Product product = getProductById(productId);

        Cart cart = Cart.create(user, product, quantity);
        cartRepository.save(cart);
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}

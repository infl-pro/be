package song.mall2.domain.cart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.mall2.domain.cart.dto.CartDto;
import song.mall2.domain.cart.dto.CartIdDto;
import song.mall2.domain.cart.entity.Cart;
import song.mall2.domain.cart.repository.CartJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.notfound.exceptions.CartNotFoundException;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final UserJpaRepository userRepository;
    private final ProductJpaRepository productRepository;
    private final CartJpaRepository cartRepository;

    @Transactional
    public CartDto addCart(Long userId, Long productId, Integer quantity) {
        User user = getUser(userId);
        Product product = getProduct(productId);

        Cart cart = Cart.of(user, product, quantity);
        Cart saveCart = cartRepository.save(cart);

        return new CartDto(saveCart.getId(), saveCart.getQuantity(), saveCart.getProduct().getId(), saveCart.getProduct().getName(),
                saveCart.getProduct().getPrice(), saveCart.getProduct().getThumbnailUrl());
    }

    @Transactional
    public List<CartDto> getCartList(Long userId) {
        User user = getUser(userId);
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

        return cartList.stream()
                .map(cart -> new CartDto(cart.getId(), cart.getQuantity(), cart.getProduct().getId(),
                        cart.getProduct().getName(), cart.getProduct().getPrice(), cart.getProduct().getThumbnailUrl()))
                .toList();
    }

    @Transactional
    public void deleteCart(Long userId, List<CartIdDto> cartIdDtoList) {
        User user = getUser(userId);
        List<Long> cartIdList = cartIdDtoList.stream()
                .map(CartIdDto::getCartId)
                .toList();
        cartRepository.deleteAllByIdAndUserId(cartIdList, user.getId());
    }

    @Transactional
    public CartDto updateCartQuantity(Long userId, Long cartId, Integer quantity) {
        User user = getUser(userId);
        Cart cart = cartRepository.findByIdAndUserId(cartId, user.getId())
                .orElseThrow(() -> new CartNotFoundException("장바구니 상품을 찾을 수 없습니다."));

        cart.updateQuantity(quantity);
        Cart saveCart = cartRepository.save(cart);

        return new CartDto(saveCart.getId(), saveCart.getQuantity(), saveCart.getProduct().getId(), saveCart.getProduct().getName(),
                saveCart.getProduct().getPrice(), saveCart.getProduct().getThumbnailUrl());
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}

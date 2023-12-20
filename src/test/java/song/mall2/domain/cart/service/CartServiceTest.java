package song.mall2.domain.cart.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class CartServiceTest {
    @Autowired
    CartService cartService;

    Long userAId = 1L;
    Long productId = 1L;

    @DisplayName("장바구니 추가")
    @Test
    void addCart() {
        assertDoesNotThrow(() -> cartService.addCart(userAId, productId, 10));
    }

    @DisplayName("장바구니 조회")
    @Test
    void getCartList() {
        assertDoesNotThrow(() -> cartService.getCartList(userAId));
    }

}
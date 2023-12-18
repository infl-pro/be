package song.mall2.domain.cart.service;

import lombok.extern.slf4j.Slf4j;
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

    Long userId = 1L;
    Long productId = 1L;

    @Test
    void add() {
        assertDoesNotThrow(() -> cartService.addCart(userId, productId, 10));
    }

}
package song.mall2.domain.cart.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.cart.entity.Cart;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class CartJpaRepositoryTest {
    @Autowired
    CartJpaRepository cartRepository;

    Long userAId = 1L;

    @Test
    void cartList() {
        assertDoesNotThrow(() -> cartRepository.findAllByUserId(userAId));
    }

    @Test
    @Transactional
    void deleteCart() {
        List<Cart> cartList = cartRepository.findAllByUserId(userAId);

        List<Long> idList = cartList.stream()
                .map(Cart::getId)
                .toList();

        Integer result = cartRepository.deleteAllByIdAndUserId(idList, userAId);

        assertThat(result)
                .isEqualTo(cartList.size());
    }

}
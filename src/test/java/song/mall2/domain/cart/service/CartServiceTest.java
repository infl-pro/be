package song.mall2.domain.cart.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.cart.dto.CartIdDto;

import java.util.ArrayList;
import java.util.List;

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
    Long productAId = 1L;
    Long cart1Id = 1L;

    @DisplayName("장바구니 추가")
    @Test
    void addCart() {
        assertDoesNotThrow(() -> cartService.addCart(userAId, productAId, 10));
    }

    @DisplayName("장바구니 조회")
    @Test
    void getCartList() {
        assertDoesNotThrow(() -> cartService.getCartList(userAId));
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCart() {
        List<CartIdDto> cartIdDtoList = new ArrayList<>();
        CartIdDto cartIdDto = new CartIdDto();
        cartIdDto.setCartId(cart1Id);
        cartIdDtoList.add(cartIdDto);
        assertDoesNotThrow(() -> cartService.deleteCart(userAId, cartIdDtoList));
    }

    @Test
    void updateCartQuantity() {
        assertDoesNotThrow(() -> cartService.updateCartQuantity(userAId, cart1Id, 40));
    }

}
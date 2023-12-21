package song.mall2.domain.order.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.order.dto.OrderProductDto;
import song.mall2.domain.order.entity.OrderProduct;
import song.mall2.exception.invalid.InvalidException;
import song.mall2.exception.invalid.exceptions.InvalidUserException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class OrderProductServiceTest {
    @Autowired
    OrderProductService orderProductService;

    Long userAId = 1L;
    Long productAId = 1L;
    Long orderProductId1 = 1L;

    @DisplayName("주문된 상품 조회")
    @Test
    void getOrderProductList() {
        assertDoesNotThrow(() -> {
            List<OrderProductDto> orderProductList = orderProductService.getOrderProductListByProduct(userAId, productAId);
            log.info("size: {}", orderProductList.size());
            return orderProductList;
        });
    }

    @DisplayName("주문 상품 상태 업데이트")
    @Test
    void updateOrderProduct() {
        assertDoesNotThrow(() -> orderProductService.updateOrderProductStatus(userAId, orderProductId1, OrderProduct.Status.SHIPPING.name()));
    }

    @DisplayName("주문 상품 상태 업데이트 예외")
    @Test
    void updateOrderProductException() {
        assertThatThrownBy(() -> orderProductService.updateOrderProductStatus(2L, orderProductId1, OrderProduct.Status.SHIPPING.name()))
                .isInstanceOf(InvalidException.class);

        assertThatThrownBy(() -> orderProductService.updateOrderProductStatus(userAId, orderProductId1, "hello"))
                .isInstanceOf(InvalidException.class);
    }

}
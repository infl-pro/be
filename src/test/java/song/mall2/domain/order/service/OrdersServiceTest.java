package song.mall2.domain.order.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.order.dto.OrderProductDto;
import song.mall2.domain.order.dto.SaveOrderProductDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class OrdersServiceTest {
    @Autowired
    OrderService orderService;

    Long userAId = 1L;
    Long productAId = 1L;
    Long productBId = 2L;
    Long orderId1 = 1L;
    Long orderProductId1 = 1L;

    @DisplayName("주문 생성")
    @Test
    void saveOrder() {
        SaveOrderProductDto saveOrderProductDto1 = createOrderProductDto(productAId, 10);
        SaveOrderProductDto saveOrderProductDto2 = createOrderProductDto(productBId, 10);

        List<SaveOrderProductDto> saveOrderProductDtoList = new ArrayList<>();
        saveOrderProductDtoList.add(saveOrderProductDto1);
        saveOrderProductDtoList.add(saveOrderProductDto2);
        assertDoesNotThrow(() -> orderService.saveOrders(userAId, saveOrderProductDtoList));
    }

    @DisplayName("주문 조회")
    @Test
    void getOrderList() {
        assertDoesNotThrow(() -> orderService.getOrderList(userAId));
    }

    @DisplayName("주문에 대한 주문 상품 리스트 조회")
    @Test
    void getOrderProductList() {
        assertDoesNotThrow(() -> orderService.getOrderProductList(userAId, orderId1));
    }

    @DisplayName("주문 상품 조회")
    @Test
    void getOrderProduct() {
        OrderProductDto orderProduct = orderService.getOrderProduct(userAId, orderProductId1);

        Assertions.assertThat(orderProduct.getProductId())
                .isEqualTo(productAId);
    }

    private SaveOrderProductDto createOrderProductDto(Long productId, Integer quantity) {
        SaveOrderProductDto saveOrderProductDto = new SaveOrderProductDto();
        saveOrderProductDto.setProductId(productId);
        saveOrderProductDto.setQuantity(quantity);

        return saveOrderProductDto;
    }

}
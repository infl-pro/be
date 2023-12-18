package song.mall2.domain.order.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.order.dto.SaveOrdersDto;
import song.mall2.domain.order.dto.SaveOrderProductDto;

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

    Long userId = 1L;
    Long productAId = 1L;
    Long productBId = 2L;

    @Test
    void saveOrder() {
        SaveOrderProductDto saveOrderProductDto1 = createOrderProductDto(productAId, 10);
        SaveOrderProductDto saveOrderProductDto2 = createOrderProductDto(productBId, 10);

        SaveOrdersDto saveOrdersDto = new SaveOrdersDto();
        saveOrdersDto.getSaveOrderProductDtoList().add(saveOrderProductDto1);
        saveOrdersDto.getSaveOrderProductDtoList().add(saveOrderProductDto2);
        assertDoesNotThrow(() -> orderService.saveOrder(userId, saveOrdersDto));
    }

    private SaveOrderProductDto createOrderProductDto(Long productId, Integer quantity) {
        SaveOrderProductDto saveOrderProductDto = new SaveOrderProductDto();
        saveOrderProductDto.setProductId(productId);
        saveOrderProductDto.setQuantity(quantity);

        return saveOrderProductDto;
    }

}
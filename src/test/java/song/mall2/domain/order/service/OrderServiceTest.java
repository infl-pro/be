package song.mall2.domain.order.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.cart.dto.CartIdDto;
import song.mall2.domain.cart.repository.CartJpaRepository;
import song.mall2.domain.order.dto.OrderFormDto;
import song.mall2.domain.order.dto.OrdersDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @Autowired
    CartJpaRepository cartRepository;

    Long userAId = 1L;
    Long orders1Id = 1L;
    List<CartIdDto> cartIdDtoList = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        cartIdDtoList = cartRepository.findAllByUserId(userAId).stream()
                .map(cart -> new CartIdDto(cart.getId()))
                .toList();
    }

    @Test
    void getOrderForm() {
        OrderFormDto orderForm = orderService.getOrderForm(userAId, List.of(1L, 2L));

        assertThat(orderForm.getCustomer().getUserId())
                .isEqualTo(userAId.toString());
    }

    @Test
    void getOrder() {
        OrdersDto orders = orderService.getOrders(userAId, orders1Id);

        assertThat(orders.getOrdersId())
                .isEqualTo(orders1Id);
    }
}
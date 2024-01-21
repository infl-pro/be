package song.mall2.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import song.mall2.domain.cart.dto.CartIdDto;
import song.mall2.domain.cart.repository.CartJpaRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CartJpaRepository cartRepository;

    Long userAId = 1L;
    Long orders1Id = 1L;
    List<CartIdDto> cartIdDtoList = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        cartIdDtoList = cartRepository.findAllByUserId(userAId)
                .stream()
                .map(cart -> new CartIdDto(cart.getId()))
                .toList();
    }

    @Test
    @WithUserDetails("a")
    void getOrderForm() throws Exception {
        mockMvc.perform(get("/orders/form")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartIdDtoList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.customer.userId").value(userAId.toString()));
    }

    @Test
    @WithUserDetails("a")
    void getOrders() throws Exception {
        mockMvc.perform(get("/orders/{ordersId}", orders1Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.ordersId").value(orders1Id));
    }

}
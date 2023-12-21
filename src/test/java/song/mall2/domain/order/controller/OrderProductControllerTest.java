package song.mall2.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import song.mall2.domain.order.dto.UpdateOrderProductStatusDto;
import song.mall2.domain.order.entity.OrderProduct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
@AutoConfigureMockMvc
class OrderProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    Long productAId = 1L;
    Long orderProductId = 1L;

    @DisplayName("주문된 상품 조회")
    @Test
    @WithUserDetails("a")
    void getOrderProductList() throws Exception {
        MvcResult result = mockMvc.perform(get("/orderProduct/{productId}", productAId))
                .andExpect(status().isOk())
                .andReturn();

        log.info("{}", result.getResponse().getContentAsString());
    }

    @DisplayName("주문 상품 상태 업데이트")
    @Test
    @WithUserDetails("a")
    void postUpdateOrderProductStatus() throws Exception {
        UpdateOrderProductStatusDto updateOrderProductStatusDto = new UpdateOrderProductStatusDto();
        updateOrderProductStatusDto.setStatusName(OrderProduct.Status.SHIPPING.name());
        String content = objectMapper.writeValueAsString(updateOrderProductStatusDto);

        mockMvc.perform(post("/orderProduct/{orderProductId}/update", orderProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

}
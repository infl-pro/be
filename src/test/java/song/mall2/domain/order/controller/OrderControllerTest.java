package song.mall2.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
import song.mall2.domain.order.dto.SaveOrderProductDto;
import song.mall2.domain.order.dto.SaveOrdersDto;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    ObjectMapper objectMapper = new ObjectMapper();
    Long productAId = 1L;
    Long productBId = 2L;
    Long ordersId = 1L;
    Long orderProductId = 1L;

    @DisplayName("주문 생성")
    @Test
    @WithUserDetails("a")
    @Transactional
    void postSaveOrder() throws Exception {
        SaveOrderProductDto saveOrderProductDto1 = createOrderProductDto(productAId, 10);
        SaveOrderProductDto saveOrderProductDto2 = createOrderProductDto(productBId, 5);

        SaveOrdersDto saveOrdersDto = new SaveOrdersDto();
        saveOrdersDto.getSaveOrderProductDtoList().add(saveOrderProductDto1);
        saveOrdersDto.getSaveOrderProductDtoList().add(saveOrderProductDto2);
        String content = objectMapper.writeValueAsString(saveOrdersDto);

        mockMvc.perform(post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @DisplayName("주문 조회")
    @Test
    @WithUserDetails("a")
    void getOrderList() throws Exception {
        MvcResult result = mockMvc.perform(get("/orders"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        log.info("{}", content);
    }

    @DisplayName("주문 상품 조회")
    @Test
    @WithUserDetails("a")
    void getOrderProductList() throws Exception {
        MvcResult result = mockMvc.perform(get("/orders/{ordersId}", ordersId))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        log.info("{}", content);
    }

    @Test
    @WithUserDetails("a")
    void getOrderProduct() throws Exception {
        MvcResult result = mockMvc.perform(get("/orders/orderProduct/{orderProductId}", orderProductId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderProductId))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        log.info("{}", content);
    }

    private SaveOrderProductDto createOrderProductDto(Long productId, Integer quantity) {
        SaveOrderProductDto saveOrderProductDto = new SaveOrderProductDto();
        saveOrderProductDto.setProductId(productId);
        saveOrderProductDto.setQuantity(quantity);

        return saveOrderProductDto;
    }

}
package song.mall2.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import song.mall2.domain.order.dto.SaveOrderProductDto;
import song.mall2.domain.order.dto.SaveOrdersDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    @WithUserDetails("a")
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

    private SaveOrderProductDto createOrderProductDto(Long productId, Integer quantity) {
        SaveOrderProductDto saveOrderProductDto = new SaveOrderProductDto();
        saveOrderProductDto.setProductId(productId);
        saveOrderProductDto.setQuantity(quantity);

        return saveOrderProductDto;
    }

}
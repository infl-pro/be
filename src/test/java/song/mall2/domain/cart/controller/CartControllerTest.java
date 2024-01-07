package song.mall2.domain.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import song.mall2.domain.cart.dto.SaveCartDto;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
@AutoConfigureMockMvc
class CartControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    Long cart1Id = 1L;

    @Test
    @WithUserDetails("a")
    void getCartList() throws Exception {
        MvcResult result = mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        log.info("{}", content);
    }

    @Test
    @WithUserDetails("a")
    @Transactional
    void postAddCart() throws Exception {
        SaveCartDto saveCartDto = new SaveCartDto();
        saveCartDto.setProductId(1L);
        saveCartDto.setQuantity(10);
        String content = objectMapper.writeValueAsString(saveCartDto);

        mockMvc.perform(post("/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("a")
    @Transactional
    void postDeleteCart() throws Exception {
        mockMvc.perform(post("/cart/delete/{cartId}", cart1Id))
                .andExpect(status().isOk());
    }

}
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
import song.mall2.domain.cart.dto.CartIdDto;
import song.mall2.domain.cart.dto.SaveCartDto;
import song.mall2.domain.cart.dto.UpdateCartQuantity;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    ObjectMapper objectMapper;

    Long cart1Id = 1L;

    @Test
    @WithUserDetails("a")
    void cartList() throws Exception {
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
    void addCart() throws Exception {
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
    void deleteCart() throws Exception {
        CartIdDto cartIdDto = new CartIdDto(cart1Id);
        List<CartIdDto> cartIdList = new ArrayList<>();
        cartIdList.add(cartIdDto);

        mockMvc.perform(delete("/cart/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartIdList)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("a")
    @Transactional
    void updateCartQuantity() throws Exception {
        UpdateCartQuantity cartQuantity = new UpdateCartQuantity(cart1Id, 100);

        mockMvc.perform(patch("/cart/updateQuantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartQuantity)))
                .andExpect(status().isOk());
    }

}
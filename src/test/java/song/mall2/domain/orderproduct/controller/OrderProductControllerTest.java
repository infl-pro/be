package song.mall2.domain.orderproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import song.mall2.domain.common.dto.PageDto;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
@AutoConfigureMockMvc
public class OrderProductControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    Long lmoodPro1Id = 25L;

    @Test
    @WithUserDetails("lmood")
    void getOrderProductList() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/orderProduct/{productId}", lmoodPro1Id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPage").isNumber())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        PageDto page = objectMapper.treeToValue(objectMapper.readTree(content).path("data"), PageDto.class);
        log.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(page));
    }

}
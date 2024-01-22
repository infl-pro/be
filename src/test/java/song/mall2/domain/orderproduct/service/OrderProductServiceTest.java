package song.mall2.domain.orderproduct.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.common.dto.PageDto;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class OrderProductServiceTest {
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private ObjectMapper objectMapper;

    Long lmoodId = 2L;
    Long product1Id = 1L;

    @Test
    void getOrderProductList() throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(0, 10);

        PageDto orderProductList = orderProductService.getOrderProductList(product1Id, lmoodId, pageRequest);

        Assertions.assertThat(orderProductList.getTotalElements())
                .isEqualTo(1);

        log.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderProductList));
    }

}
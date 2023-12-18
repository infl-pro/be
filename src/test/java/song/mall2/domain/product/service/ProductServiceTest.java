package song.mall2.domain.product.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.product.dto.SaveProductDto;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class ProductServiceTest {
    @Autowired
    ProductService productService;

    Long userId = 1L;

    @Test
    void save() {
        SaveProductDto saveProductDto = new SaveProductDto();
        saveProductDto.setName("test product");
        saveProductDto.setPrice(10000);
        saveProductDto.setDescription("test description");
        saveProductDto.setStockQuantity(100);

        assertDoesNotThrow(() -> productService.save(userId, saveProductDto));
    }

}
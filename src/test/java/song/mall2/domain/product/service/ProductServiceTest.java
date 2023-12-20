package song.mall2.domain.product.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
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

    Long userAId = 1L;
    Long productAId = 1L;

    @DisplayName("상품 등록")
    @Test
    void saveProduct() {
        SaveProductDto saveProductDto = new SaveProductDto();
        saveProductDto.setName("test product");
        saveProductDto.setPrice(10000);
        saveProductDto.setDescription("test description");
        saveProductDto.setStockQuantity(100);

        assertDoesNotThrow(() -> productService.saveProduct(userAId, saveProductDto));
    }

    @DisplayName("상품 상세 조회")
    @Test
    void getProduct() {
        assertDoesNotThrow(() -> productService.getProduct(productAId));
    }

}
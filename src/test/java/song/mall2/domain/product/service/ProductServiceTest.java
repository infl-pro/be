package song.mall2.domain.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.common.dto.PageDto;
import song.mall2.domain.product.dto.ProductDto;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.entity.Product;

import static org.assertj.core.api.Assertions.*;
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

    ObjectMapper objectMapper = new ObjectMapper();

    Long userAId = 1L;
    Long lmoodId = 2L;
    Long lmoodPro1Id = 25L;

    @DisplayName("상품 등록")
    @Test
    void saveProduct() {
        SaveProductDto saveProductDto = new SaveProductDto();
        saveProductDto.setName("test product");
        saveProductDto.setPrice(10000);
        saveProductDto.setDescription("test description");
        saveProductDto.setStockQuantity(100);
        saveProductDto.setCategoryName(Product.Category.TOP.name());

        assertDoesNotThrow(() -> productService.saveProduct(userAId, saveProductDto));
    }

    @DisplayName("상품 상세 조회")
    @Test
    void getProduct() {
        assertDoesNotThrow(() -> productService.getProduct(lmoodPro1Id));
    }

    @Test
    void editProduct() {
        SaveProductDto editForm = new SaveProductDto();
        editForm.setName("edit name");
        editForm.setDescription("edit description");
        editForm.setPrice(1);

        ProductDto productDto = productService.editProduct(lmoodPro1Id, lmoodId, editForm);

        assertThat(productDto.getProductName())
                .isEqualTo(editForm.getName());
    }

    @Test
    void getProductList() throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageDto productList = productService.findProductList(pageRequest);

        String page = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(productList);
        log.info("{}", page);
    }
}
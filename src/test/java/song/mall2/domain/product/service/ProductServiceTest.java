package song.mall2.domain.product.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.product.dto.EditProductDto;
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
        saveProductDto.setCategoryName(Product.Category.A.name());

        assertDoesNotThrow(() -> productService.saveProduct(userAId, saveProductDto));
    }

    @DisplayName("상품 상세 조회")
    @Test
    void getProduct() {
        assertDoesNotThrow(() -> productService.getProduct(productAId));
    }

    @Test
    void getEditForm() {
        EditProductDto editForm = productService.getEditForm(productAId, userAId);
        assertThat(editForm.getName())
                .isEqualTo(productService.getProduct(productAId).getProductName());
    }

    @Test
    void editProduct() {
        EditProductDto editForm = productService.getEditForm(productAId, userAId);
        editForm.setName("edit name");
        editForm.setPrice(1);

        ProductDto productDto = productService.editProduct(productAId, userAId, editForm);

        assertThat(productDto.getProductName())
                .isEqualTo(editForm.getName());
    }

}
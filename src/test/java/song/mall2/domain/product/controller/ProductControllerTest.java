package song.mall2.domain.product.controller;

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
import song.mall2.domain.product.dto.EditProductDto;
import song.mall2.domain.product.dto.SaveProductDto;
import song.mall2.domain.product.dto.UpdateProductStockQuantityDto;
import song.mall2.domain.product.entity.Product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    Long product1Id = 1L;

    @Test
    @WithUserDetails("a")
    void postSaveProduct() throws Exception {
        SaveProductDto saveProductDto = new SaveProductDto();
        saveProductDto.setName("test name");
        saveProductDto.setPrice(10000);
        saveProductDto.setDescription("test description");
        saveProductDto.setStockQuantity(100);
        saveProductDto.setThumbnailUrl("testUrl");
        saveProductDto.setProductImgUrl("testImg");
        saveProductDto.setCategoryName(Product.Category.TOP.name());

        mockMvc.perform(post("/product/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("test name"));
    }

    @Test
    void getProduct() throws Exception {
        mockMvc.perform(get("/product/{productId}", product1Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(product1Id));
    }

    @Test
    @WithUserDetails("b")
    void patchProductEdit() throws Exception {
        EditProductDto editProductDto = new EditProductDto();
        editProductDto.setProductName("edit name");
        editProductDto.setProductPrice(1);
        editProductDto.setProductDescription("edit description");
        editProductDto.setThumbnailUrl("edit thumbnail");
        editProductDto.setImgUrl("edit img");

        mockMvc.perform(patch("/product/{productId}/edit", product1Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("edit name"));
    }

    @Test
    @WithUserDetails("b")
    void patchUpdateStockQuantity() throws Exception {
        UpdateProductStockQuantityDto stockQuantityDto = new UpdateProductStockQuantityDto();
        stockQuantityDto.setStockQuantity(10000);

        mockMvc.perform(patch("/product/{productId}/updateStockQuantity", product1Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockQuantityDto)))
                .andExpect(status().isOk());
    }
}
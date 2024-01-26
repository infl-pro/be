package song.mall2.domain.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
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

    Long product1Id = 1L;

    @Test
    @WithUserDetails("a")
    void postSaveProduct() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "thumbnail",
                "test.png",
                "image/png",
                "1".getBytes()
        );
        mockMvc.perform(multipart("/product/save")
                        .file(mockMultipartFile)
                        .param("name", "test name")
                        .param("price", "10000")
                        .param("description", "test description")
                        .param("stockQuantity", "100")
                        .param("categoryName", Product.Category.TOP.name())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.productName").value("test name"));
    }

    @Test
    void getProduct() throws Exception {
        mockMvc.perform(get("/product/{productId}", product1Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.productId").value(product1Id));
    }

    @Test
    @WithUserDetails("northfc")
    void patchProductEdit() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "thumbnail",
                "test.png",
                "image/png",
                "1".getBytes()
        );

        mockMvc.perform(multipart("/product/1/edit")
                        .file(mockMultipartFile)
                        .with(request -> {
                            request.setMethod("PATCH");
                            return request;
                        })
                        .param("name", "edit name")
                        .param("price", "1")
                        .param("description", "edit description")
                        .param("stockQuantity", "100")
                        .param("categoryName", Product.Category.TOP.name())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.productName").value("edit name"));
    }
}
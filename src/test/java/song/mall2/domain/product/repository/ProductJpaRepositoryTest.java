package song.mall2.domain.product.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.product.entity.Product;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class ProductJpaRepositoryTest {
    @Autowired
    ProductJpaRepository productRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findAll() throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> productPage = productRepository.findAll(pageRequest);

        log.info("{}", productPage.getTotalElements());
    }

    @Test
    void finsAllBySearch() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> productPage = productRepository.findAllBySearch(pageRequest, "");

        log.info("{}", productPage.getTotalElements());
    }

    @Test
    void findAllByCategory() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> productPage = productRepository.findAllByCategory(pageRequest, Product.Category.TOP);

        log.info("{}", productPage.getTotalElements());
    }

    @Test
    void findAllBySearchAndCategory() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> productPage = productRepository.findAllBySearchAndCategory(pageRequest, "니트", Product.Category.TOP);

        log.info("{}", productPage.getTotalElements());
    }
}
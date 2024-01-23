package song.mall2.domain.favorite.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class FavoriteJpaRepositoryTest {
    @Autowired
    FavoriteJpaRepository favoriteRepository;

    Long userAId = 1L;
    Long product1Id = 1L;

    @Test
    void exists() {
        assertThat(favoriteRepository.findByUserIdAndProductId(userAId, product1Id))
                .isEmpty();
    }

}
package song.mall2.domain.favorite.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.favorite.dto.FavoriteDto;
import song.mall2.domain.favorite.repository.FavoriteJpaRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class FavoriteServiceTest {
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    FavoriteJpaRepository favoriteRepository;

    Long userAId = 1L;
    Long product1Id = 1L;
    Long product5Id = 5L;

    @Test
    void addFavorite() {
        FavoriteDto favorite = favoriteService.addFavorite(userAId, product5Id);

        assertThat(favorite.getUserId())
                .isEqualTo(userAId);
    }

    @Test
    void deleteFavorite() {
        favoriteService.deleteFavorite(userAId, product1Id);

        assertThat(favoriteRepository.findByUserIdAndProductId(userAId, product1Id))
                .isEmpty();
    }

}
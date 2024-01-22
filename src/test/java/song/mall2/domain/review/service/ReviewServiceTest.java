package song.mall2.domain.review.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.mall2.domain.review.dto.ReviewDto;
import song.mall2.domain.review.dto.SaveReviewDto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.profiles.active=test",
        "JASYPT_ENCRYPTOR_PASSWORD=test"
})
class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    ObjectMapper objectMapper;

    Long userAId = 1L;
    Long orderProduct1Id = 1L;

    @Test
    void saveReview() throws JsonProcessingException {
        SaveReviewDto testReview = new SaveReviewDto("test review", 5);
        ReviewDto reviewDto = reviewService.saveReview(orderProduct1Id, userAId, testReview);

        assertThat(reviewDto.getContent())
                .isEqualTo(testReview.getContent());
        log.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reviewDto));
    }

}
package song.mall2.domain.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import song.mall2.domain.review.dto.SaveReviewDto;
import song.mall2.domain.review.service.ReviewService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

@Slf4j
@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{orderProductId}/save")
    public void postSaveReview(@PathVariable("orderProductId") Long orderProductId,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestBody SaveReviewDto saveReviewDto) {
        reviewService.saveReview(orderProductId, userPrincipal.getId(), saveReviewDto);

    }
}

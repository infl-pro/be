package song.mall2.domain.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import song.mall2.domain.review.dto.SaveReviewDto;
import song.mall2.domain.review.service.ReviewService;
import song.mall2.security.authentication.userprincipal.UserPrincipal;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{orderProductId}/save")
    public void postSaveReview(@PathVariable("orderProductId") Long orderProductId,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestBody SaveReviewDto saveReviewDto) {
        reviewService.saveReview(orderProductId, userPrincipal.getId(), saveReviewDto);

    }
}

package song.mall2.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String username;
    private Long orderProductId;
    private Long productId;
    private String content;
    private Integer rating;
    private String createAt;

    public ReviewDto(Long id, String username, Long orderProductId, Long productId, String content, Integer rating, LocalDateTime createAt) {
        this.id = id;
        this.username = username;
        this.orderProductId = orderProductId;
        this.productId = productId;
        this.content = content;
        this.rating = rating;
        this.createAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(createAt);
    }
}

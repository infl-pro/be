package song.mall2.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
public class ProductReviewDto {
    private Long id;
    private String username;
    private String content;
    private Integer rating;
    private String createAt;

    public ProductReviewDto(Long id, String username, String content, Integer rating, LocalDateTime createAt) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.rating = rating;
        this.createAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(createAt);
    }
}

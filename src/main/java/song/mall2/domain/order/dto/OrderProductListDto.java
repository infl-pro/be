package song.mall2.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductListDto {
    private Long productId;
    private String productName;
    private String productThumbnailUrl;

    private String createAt;
    private Long ordersId;

    private Integer quantity;
    private Integer amount;
    private String status;

    public OrderProductListDto(Long productId, String productName, String productThumbnailUrl, LocalDateTime createAt, Long ordersId, Integer quantity, Integer amount, String status) {
        this.productId = productId;
        this.productName = productName;
        this.productThumbnailUrl = productThumbnailUrl;
        this.createAt = DateTimeFormatter.ofPattern("yyyy.MM.dd").format(createAt);
        this.ordersId = ordersId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
    }
}

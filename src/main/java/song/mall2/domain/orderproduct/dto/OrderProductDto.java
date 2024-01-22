package song.mall2.domain.orderproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto {
    private Long productId;
    private String productName;
    private String productThumbnail;
    private String status;
    private Integer amount;
    private Integer quantity;
}

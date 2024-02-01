package song.mall2.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDto {
    private Long productId;
    private String productName;
    private Integer productPrice;
    private String productThumbnail;
    private String sellerName;
}

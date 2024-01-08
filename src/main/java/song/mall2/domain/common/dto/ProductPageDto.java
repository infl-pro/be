package song.mall2.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageDto {
    private Long productId;
    private String productName;
    private Integer productPrice;
    private String productThumbnail;

    private String brandName;
}

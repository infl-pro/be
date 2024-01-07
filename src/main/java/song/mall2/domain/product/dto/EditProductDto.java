package song.mall2.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProductDto {
    private String productName;
    private Integer productPrice;
    private String productDescription;
    private String thumbnailUrl;
    private String imgUrl;
    private String category;
}

package song.mall2.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProductDto {
    private String name;
    private Integer price;
    private String description;
    private String thumbnailUrl;
    private String imgUrl;
    private Integer stockQuantity;
}

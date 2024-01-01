package song.mall2.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductDto {
    private String name;
    private Integer price;
    private String description;
    private Integer stockQuantity;
    private String thumbnailUrl;
    private String productImgUrl;
}

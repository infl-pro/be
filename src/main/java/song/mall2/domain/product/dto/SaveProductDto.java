package song.mall2.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveProductDto {
    private String name;
    private Integer price;
    private String description;
    private Integer stockQuantity;
}

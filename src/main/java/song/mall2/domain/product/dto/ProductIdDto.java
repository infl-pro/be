package song.mall2.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductIdDto {
    private Long productId;

    public ProductIdDto(Long productId) {
        this.productId = productId;
    }

    public ProductIdDto() {
    }
}

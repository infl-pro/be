package song.mall2.domain.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveCartDto {
    private Long productId;
    private Integer quantity;
}

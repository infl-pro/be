package song.mall2.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveOrderProductDto {
    private Long productId;
    private Integer quantity;
}

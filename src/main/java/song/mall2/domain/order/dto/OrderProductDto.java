package song.mall2.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderProductDto {
    private Long id;
    private Integer quantity;
    private String status;

    private Long productId;
    private String productName;

    public OrderProductDto(Long id, Integer quantity, String status, Long productId, String productName) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;

        this.productId = productId;
        this.productName = productName;
    }
}

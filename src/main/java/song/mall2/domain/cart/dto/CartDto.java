package song.mall2.domain.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDto {
    private Long id;
    private Integer quantity;

    private Long productId;
    private String productName;
    private Integer productPrice;

    public CartDto(Long id, Integer quantity, Long productId, String productName, Integer productPrice) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }
}

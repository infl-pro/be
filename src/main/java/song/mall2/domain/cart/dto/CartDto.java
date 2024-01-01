package song.mall2.domain.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDto {
    private Long cartId;
    private Integer quantity;

    private Long productId;
    private String productName;
    private Integer productPrice;

    public CartDto(Long cartId, Integer quantity, Long productId, String productName, Integer productPrice) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }
}

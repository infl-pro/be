package song.mall2.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CartDto {
    private Long cartId;
    private Integer quantity;

    private Long productId;
    private String productName;
    private Integer productPrice;
    private String productThumbnailUrl;

    public CartDto(Long cartId, Integer quantity, Long productId, String productName, Integer productPrice, String productThumbnailUrl) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productThumbnailUrl = productThumbnailUrl;
    }
}

package song.mall2.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String productName;
    private Integer price;
    private String description;
    private String thumbnailUrl;
    private String imgUrl;
    private Integer stockQuantity;

    private String username;

    public ProductDto(Long productId, String productName, Integer price, String description, String thumbnailUrl, String imgUrl, Integer stockQuantity, String username) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.imgUrl = imgUrl;
        this.stockQuantity = stockQuantity;

        this.username = username;
    }
}

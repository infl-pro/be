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
    private Integer stockQuantity;
    private String category;
    private String sellerName;

    private boolean hasPurchased = false;
    private boolean isSeller = false;

    public ProductDto(Long productId, String productName, Integer price, String description, String thumbnailUrl, Integer stockQuantity,
                      String category, String sellerName) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.sellerName = sellerName;
    }

    public ProductDto(Long productId, String productName, Integer price, String description, String thumbnailUrl, Integer stockQuantity,
                      String category, String sellerName, boolean hasPurchased, boolean isSeller) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.sellerName = sellerName;

        this.hasPurchased = hasPurchased;
        this.isSeller = isSeller;
    }
}

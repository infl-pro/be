package song.mall2.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    private boolean isPurchased = false;
    private boolean isSeller = false;

    private List<ProductReviewDto> reviewList = new ArrayList<>();
//    private Integer favorite;
//    private boolean isFavorite;

    public ProductDto(Long productId, String productName, Integer price, String description, String thumbnailUrl, Integer stockQuantity,
                      String category, String sellerName, List<ProductReviewDto> reviewList) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.sellerName = sellerName;
        this.reviewList = reviewList;
    }

    public ProductDto(Long productId, String productName, Integer price, String description, String thumbnailUrl, Integer stockQuantity,
                      String category, String sellerName, List<ProductReviewDto> reviewList, boolean isPurchased, boolean isSeller) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.sellerName = sellerName;
        this.reviewList = reviewList;

        this.isPurchased = isPurchased;
        this.isSeller = isSeller;
    }
}

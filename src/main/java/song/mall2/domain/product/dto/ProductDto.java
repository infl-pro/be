package song.mall2.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.mall2.domain.img.dto.ImageDto;
import song.mall2.domain.product.entity.Product;

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

    private List<ImageDto> imgList = new ArrayList<>();

//    private List<ProductReviewDto> reviewList = new ArrayList<>();
//    private Integer favorite;
//    private boolean isFavorite;

    public ProductDto(Product product, List<ImageDto> imageList) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.thumbnailUrl = product.getThumbnailUrl();
        this.stockQuantity = product.getStockQuantity();
        this.category = product.getCategory().name();
        this.sellerName = product.getUser().getName();
        this.imgList = imageList;
    }

    public ProductDto(Product product, List<ImageDto> imageList, boolean isPurchased, boolean isSeller) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.thumbnailUrl = product.getThumbnailUrl();
        this.stockQuantity = product.getStockQuantity();
        this.category = product.getCategory().name();
        this.sellerName = product.getUser().getName();
        this.imgList = imageList;

        this.isPurchased = isPurchased;
        this.isSeller = isSeller;
    }
}

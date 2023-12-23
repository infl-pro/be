package song.mall2.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private Integer stockQuantity;

    private Long userId;
    private String username;

    public ProductDto(Long id, String name, Integer price, String description, Integer stockQuantity, Long userId, String username) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;

        this.userId = userId;
        this.username = username;
    }
}

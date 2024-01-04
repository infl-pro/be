package song.mall2.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.user.entity.User;
import song.mall2.exception.invalid.exceptions.InvalidStockQuantityException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String name;
    private Integer price;
    private String description;
    private String thumbnailUrl;
    private String imgUrl;
    private Integer stockQuantity;

    private Product(User user, String name, Integer price, String description, String thumbnailUrl, String imgUrl, Integer stockQuantity) {
        this.user = user;
        this.name = name;
        this.price = price;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.imgUrl = imgUrl;
        this.stockQuantity = stockQuantity;
    }

    public static Product create(User user, String name, Integer price, String description, String thumbnailUrl, String imgUrl, Integer stockQuantity) {
        return new Product(user, name, price, description, thumbnailUrl, imgUrl, stockQuantity);
    }

    public void decreaseStockQuantity(Integer quantity) {
        if (stockQuantity - quantity < 0) {
            throw new InvalidStockQuantityException();
        }

        stockQuantity -= quantity;
    }

    public void increaseStockQuantity(Integer quantity) {
        this.stockQuantity += quantity;
    }

    public void update(String name, Integer price, String description, String thumbnailUrl, String imgUrl, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.imgUrl = imgUrl;
        this.stockQuantity = stockQuantity;
    }
}

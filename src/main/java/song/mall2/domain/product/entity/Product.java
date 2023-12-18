package song.mall2.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.user.entity.User;

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
    private Integer stockQuantity;

    private Product(User user, String name, Integer price, String description, Integer stockQuantity) {
        this.user = user;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

    public static Product create(User user, String name, Integer price, String description, Integer stockQuantity) {
        return new Product(user, name, price, description, stockQuantity);
    }
}

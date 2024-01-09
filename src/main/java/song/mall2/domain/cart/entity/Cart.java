package song.mall2.domain.cart.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Integer quantity;

    private Cart(User user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public static Cart create(User user, Product product, Integer quantity) {
        return new Cart(user, product, quantity);
    }

    public Integer getAmount() {
        return quantity * product.getPrice();
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

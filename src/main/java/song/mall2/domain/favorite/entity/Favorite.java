package song.mall2.domain.favorite.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
    @Id @GeneratedValue
    Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Favorite(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public static Favorite create(User user, Product product) {
        return new Favorite(user, product);
    }
}

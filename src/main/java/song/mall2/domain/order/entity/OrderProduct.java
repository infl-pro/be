package song.mall2.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.user.entity.User;
import song.mall2.exception.invalid.exceptions.InvalidRequestException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "orders_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer quantity;
    private Integer amount;

    private OrderProduct(Orders orders, Product product, User user, Integer quantity, Integer amount) {
        this.orders = orders;
        this.product = product;
        this.user = user;
        this.status = Status.PAID;
        this.quantity = quantity;
        this.amount = amount;
    }

    public static OrderProduct create(Orders orders, Product product, User user, Integer quantity) {
        Integer amount = product.getPrice() * quantity;
        product.decreaseStockQuantity(quantity);
        return new OrderProduct(orders, product, user, quantity, amount);
    }

    public void cancel() {
        this.status = Status.CANCELLED;

        this.product.increaseStockQuantity(quantity);
    }

    public enum Status {
        PREPARING, PAID, SHIPPING, COMPLETED, CANCELLED;

        public static Status of(String statusName) {
            for (Status status : values()) {
                if (statusName.equals(status.name())) {
                    return status;
                }
            }
            throw new InvalidRequestException();
        }
    }
}

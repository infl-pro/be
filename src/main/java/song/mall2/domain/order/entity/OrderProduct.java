package song.mall2.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.product.entity.Product;
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

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer quantity;

    private OrderProduct(Orders orders, Product product, Integer quantity) {
        this.orders = orders;
        this.product = product;
        this.status = Status.PREPARING;
        this.quantity = quantity;
    }

    public static OrderProduct create(Orders orders, Product product, Integer quantity) {
        return new OrderProduct(orders, product, quantity);
    }

    public void updateStatus(Long userId, String statusName) {
        product.isSeller(userId);

        this.status = Status.of(statusName);
    }

    public enum Status {
        PREPARING, SHIPPING, COMPLETED, CANCELLED;

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

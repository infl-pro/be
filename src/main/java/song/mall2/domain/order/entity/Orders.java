package song.mall2.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Orders(User user) {
        this.user = user;
        this.status = Status.READY;
    }

    public static Orders create(User user) {
        return new Orders(user);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProductList.add(orderProduct);

//        amount = orderProduct.getProduct().getPrice() * orderProduct.getQuantity();
        amount = 100;
    }

    public Integer getAmount() {
        return 100;
    }

    public void paid() {
        this.status = Status.PAID;
    }

    public void cancel() {
        this.status = Status.CANCELLED;
    }

    public enum Status {
        READY, CANCELLED, PAID, COMPLETE,
    }
}

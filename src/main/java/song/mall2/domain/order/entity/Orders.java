package song.mall2.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.common.entity.BaseTimeEntity;
import song.mall2.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    private Orders(User user) {
        this.user = user;
    }

    public static Orders create(User user) {
        return new Orders(user);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProductList.add(orderProduct);
    }
}

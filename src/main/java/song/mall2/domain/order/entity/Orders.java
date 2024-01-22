package song.mall2.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import song.mall2.domain.orderproduct.entity.OrderProduct;
import song.mall2.domain.payment.entity.Payment;
import song.mall2.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "payment")
    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    private Orders(User user, Payment payment) {
        this.user = user;
        this.payment = payment;
    }

    public static Orders create(User user, Payment payment) {
        return new Orders(user, payment);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProductList.add(orderProduct);
    }

    public boolean isBuyer(Long userId) {
        return this.user.getId().equals(userId);
    }
}

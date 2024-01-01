package song.mall2.domain.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.user.entity.User;
import song.mall2.exception.invalid.exceptions.InvalidUserException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "orders_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;

    private String paymentId;
    private String status;
    private Integer totalAmount;
    private String paidAt;
    private String cancelledAt;
    private String failedAt;

    private Payment(User user, Orders orders, String paymentId, String status, Integer totalAmount, String paidAt, String cancelledAt, String failedAt) {
        this.user = user;
        this.orders = orders;
        this.paymentId = paymentId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paidAt = paidAt;
        this.cancelledAt = cancelledAt;
        this.failedAt = failedAt;
    }

    public static Payment of(User user, Orders orders, String paymentId, String status, Integer totalAmount, String paidAt, String cancelledAt, String failedAt) {
        return new Payment(user, orders, paymentId, status, totalAmount, paidAt, cancelledAt, failedAt);
    }

    public void isBuyer(Long userId) {
        if (!userId.equals(user.getId())) {
            throw new InvalidUserException("권한이 없습니다.");
        }
    }

    public void update(String status, String paidAt, String cancelledAt, String failedAt) {
        this.status = status;
        this.paidAt = paidAt;
        this.cancelledAt = cancelledAt;
        this.failedAt = failedAt;
    }
}

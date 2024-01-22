package song.mall2.domain.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String paymentId;
    private String status;
    private Integer totalAmount;
    private String paidAt;
    private String cancelledAt;
    private String failedAt;
    private String addressLine;

    private Payment(User user, String paymentId, String status, Integer totalAmount, String paidAt, String cancelledAt, String failedAt,
                    String addressLine) {
        this.user = user;
        this.paymentId = paymentId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paidAt = paidAt;
        this.cancelledAt = cancelledAt;
        this.failedAt = failedAt;
        this.addressLine = addressLine;
    }

    public static Payment of(User user, String paymentId, String status, Integer totalAmount, String paidAt, String cancelledAt, String failedAt,
                             String addressLine) {
        return new Payment(user, paymentId, status, totalAmount, paidAt, cancelledAt, failedAt, addressLine);
    }

    public boolean isBuyer(Long userId) {
        return userId.equals(user.getId());
    }

    public void update(String status, String paidAt, String cancelledAt, String failedAt) {
        this.status = status;
        this.paidAt = paidAt;
        this.cancelledAt = cancelledAt;
        this.failedAt = failedAt;
    }

    public void cancel(String cancelledAt) {
        this.status = "Cancelled";
        this.cancelledAt = cancelledAt;
    }
}

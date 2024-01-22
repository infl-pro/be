package song.mall2.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import song.mall2.domain.orderproduct.entity.OrderProduct;
import song.mall2.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "orderProduct_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderProduct orderProduct;

    private String content;
    private Integer rating;
    @CreatedDate
    private LocalDateTime createAt;

    private Review(User user, OrderProduct orderProduct, String content, Integer rating) {
        this.user = user;
        this.orderProduct = orderProduct;
        this.content = content;
        this.rating = rating;
    }

    public static Review create(User user, OrderProduct orderProduct, String content, Integer rating) {
        return new Review(user, orderProduct, content, rating);
    }
}

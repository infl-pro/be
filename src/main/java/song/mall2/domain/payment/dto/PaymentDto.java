package song.mall2.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String paymentId;
    private Integer totalAmount;
    private String status;
    private Long orderId;
}

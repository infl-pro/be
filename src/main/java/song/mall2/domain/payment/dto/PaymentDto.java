package song.mall2.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private String paymentId;
    private Integer totalAmount;
    private String paidAt;
    private String failedAt;
    private String cancelledAt;
    private String status;
    private String addressLine;
}

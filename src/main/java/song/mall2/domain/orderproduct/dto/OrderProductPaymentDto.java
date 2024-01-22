package song.mall2.domain.orderproduct.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductPaymentDto {
    private Long id;
    private Long userId;
    private Long ordersId;
    private String paymentId;
    private String status;
    private Integer amount;
}

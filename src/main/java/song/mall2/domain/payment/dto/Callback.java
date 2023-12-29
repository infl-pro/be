package song.mall2.domain.payment.dto;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Callback {
    private String txId;
    private String transactionType;
    private String paymentId;
    private String code;
    private String message;
}

package song.mall2.domain.payment.dto;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Webhook {
    private String imp_uid;
    private String merchant_uid;
    private String tx_id;
    private String payment_id;
    private String status;
}

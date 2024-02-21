package song.mall2.domain.payment.dto;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebhookRequest {
    private String imp_uid;
    private String merchant_uid;
//    public Transaction Transaction;
    private String tx_id;
    private String payment_id;
    private String status;

    @ToString
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transaction {
        private String tx_id;
        private String payment_id;
        private String status;
    }
}

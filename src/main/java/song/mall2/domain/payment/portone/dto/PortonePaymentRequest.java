package song.mall2.domain.payment.portone.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortonePaymentRequest {
    private String storeId;
    private String paymentId;
    private String orderName;
    private Long totalAmount;
    private String currency;
    private String payMethod;
    private String channelKey;
    private Customer customer;
    private String redirectUrl;
    private Long customData;

    public PortonePaymentRequest(String storeId, Long orderId, Integer totalAmount, String channelKey, Long userId) {
        this.storeId = storeId;
        this.paymentId = "pid-" + UUID.randomUUID().toString().substring(0, 8);
        this.orderName = "order-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm"));
        this.totalAmount = Long.valueOf(totalAmount);
        this.currency = "KRW";
        this.payMethod = "CARD";
        this.channelKey = channelKey;
        this.customer = new Customer(userId);
        this.redirectUrl = "/payment/callback";
        this.customData = orderId;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        private Long userId;
        private Address address = new Address();

        public Customer(Long userId) {
            this.userId = userId;
        }

        @Getter @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Address {
            private String country = "KR";
            private String addressLine1 = "서울시";
            private String addressLine2 = "집";
        }
    }
}

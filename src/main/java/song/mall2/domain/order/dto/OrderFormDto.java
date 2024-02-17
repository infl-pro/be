package song.mall2.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.mall2.domain.user.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFormDto {
    private String storeId;
    private String paymentId;
    private String payMethod;
    private String channelKey;
    private String redirectUrl;

    private Integer totalAmount;
    private String currency;

    private Customer customer;
    private List<Products> products = new ArrayList<>();

    private String orderName;
    private CustomData customData;

    public OrderFormDto(String storeId, String channelKey, Integer totalAmount, User user, List<Products> productsList, List<Long> cartList) {
        this.storeId = storeId;
        this.paymentId = "pid-" + UUID.randomUUID().toString().substring(0, 8);
        this.payMethod = "CARD";
        this.channelKey = channelKey;
        this.redirectUrl = "/payment";

        this.totalAmount = totalAmount;
        this.currency = "KRW";

        this.customer = new Customer(user.getId().toString(), user.getName(), user.getEmail());
        this.products = productsList;

        this.orderName = "order-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm"));
        this.customData = new CustomData(cartList == null ? new ArrayList<>() : cartList);
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        private String userId;
        private Address address = new Address();
        private String name;
        private String email;

        public Customer(String userId, String name, String email) {
            this.userId = userId;
            this.name = name;
            this.email = email;
        }

        @Getter @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Address {
//            private String addressLine1 = "서울시";
//            private String addressLine2 = "집";
            private String addressLine1;
            private String addressLine2;
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Products {
        private String id;
        private String name;
        private Integer amount;
        private Integer quantity;
        private String sellerName;
        private String thumbnailUrl;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomData {
        private List<Long> cartList = new ArrayList<>();
    }
}

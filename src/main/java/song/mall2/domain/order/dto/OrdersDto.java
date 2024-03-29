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

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {
    private Long ordersId;
    private String createAt;
    private String name;
    private String email;
    private String address;
    private List<OrderProductDto> orderProductList = new ArrayList<>();
    private PaymentDto payment;

    public OrdersDto(Long ordersId, LocalDateTime createAt, User user, String address, List<OrderProductDto> orderProductList, Integer totalAmount, String status) {
        this.ordersId = ordersId;
        this.createAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(createAt);
        this.name = user.getName();
        this.email = user.getEmail();
        this.address = address;
        this.orderProductList = orderProductList;
        this.payment = new PaymentDto(totalAmount, status);
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentDto {
        private Integer totalAmount;
        private String status;
    }
}

package song.mall2.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<OrderProductDto> orderProductList = new ArrayList<>();
    private PaymentDto payment;

    public OrdersDto(Long ordersId, LocalDateTime createAt, List<OrderProductDto> orderProductList, Integer totalAmount, String status) {
        this.ordersId = ordersId;
        this.createAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(createAt);
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

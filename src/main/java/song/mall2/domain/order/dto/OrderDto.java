package song.mall2.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDto {
    private Long id;
    private Long totalAmount;

    public OrderDto(Long id, Long amount) {
        this.id = id;
        this.totalAmount = amount;
    }
}

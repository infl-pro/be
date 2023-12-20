package song.mall2.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDto {
    private Long id;

    public OrderDto(Long id) {
        this.id = id;
    }
}

package song.mall2.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SaveOrderDto {
    private List<SaveOrderProductDto> saveOrderProductDtoList = new ArrayList<>();
}

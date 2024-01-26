package song.mall2.domain.cart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartQuantity {
    @NotNull(message = "장바구니가 존재하지 않습니다.")
    @Positive(message = "유효하지 않은 장바구니입니다.")
    private Long cartId;
    @NotNull(message = "수량을 입력해주세요.")
    @Positive(message = "유효하지 않은 수량입니다.")
    private Integer quantity;
}

package song.mall2.domain.cart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveCartDto {
    @NotBlank(message = "상품 ID를 입력해주세요.")
    @Positive(message = "유효하지 않은 상품입니다.")
    private Long productId;
    @NotBlank(message = "수량을 입력해주세요.")
    @Positive(message = "유효하지 않은 수량입니다.")
    private Integer quantity;
}

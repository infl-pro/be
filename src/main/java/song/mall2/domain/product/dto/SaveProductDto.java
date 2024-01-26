package song.mall2.domain.product.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductDto {
    @NotBlank(message = "상품 이름을 입력해주세요.")
    @Size(min = 2, max = 255, message = "상품 이름은 2 ~ 255자 사이여야 합니다.")
    private String name;
    @NotBlank(message = "상품 가격을 입력해주세요.")
    @Min(value = 1, message = "상품 가격은 최소 1원 이상이여야 합니다.") @Max(value = 1000000, message = "상품 가격은 최대 1000000원 이하여야 합니다.")
    private Integer price;
    @NotBlank(message = "상품 설명을 입력해주세요.")
    @Size(min = 2, max = 255, message = "상품 설명은 2 ~ 255자 사이여야 합니다.")
    private String description;
    @NotBlank(message = "재고수량을 입력해주세요.")
    @Min(value = 1, message = "재고 수량은 최소 1개 이상이여야 합니다.") @Max(value = 1000, message = "재고 수량은 최대 1000개 이하여야 합니다.")
    private Integer stockQuantity;
    @NotBlank
    private String thumbnailUrl;
    @NotBlank(message = "카테고리를 입력해주세요.")
    private String categoryName;

    @Size(max = 5, message = "이미지는 최대 5개까지 등록 가능합니다.")
    private List<MultipartFile> imgList = new ArrayList<>();
}

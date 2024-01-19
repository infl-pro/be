package song.mall2.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageDto {
    List<?> content = new ArrayList<>();
    Integer totalPage;
    Long totalElements;
    Integer pageNumber;

    public ProductPageDto(Page page) {
        this.content = page.getContent();
        this.totalPage = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageNumber = page.getNumber();
    }
}

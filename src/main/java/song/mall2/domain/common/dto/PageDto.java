package song.mall2.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    List<?> content = new ArrayList<>();
    Integer totalPage;
    Long totalElements;
    Integer pageNumber;

    public PageDto(Page page) {
        this.content = Collections.unmodifiableList(page.getContent());
        this.totalPage = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageNumber = page.getNumber();
    }
}

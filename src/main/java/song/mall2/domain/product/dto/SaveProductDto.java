package song.mall2.domain.product.dto;

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
    private String name;
    private Integer price;
    private String description;
    private Integer stockQuantity;
    private String thumbnailUrl;
    private String categoryName;
    private List<MultipartFile> imgList = new ArrayList<>();
}

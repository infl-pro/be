package song.mall2.domain.favorite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {
    private Long id;

    private Long userId;
    private String username;

    private Long productId;
    private String productName;
}

package song.mall2.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostOrderFromDto {
    @NotNull
    @Size(min = 1, max = 10)
    private List<Long> cartId = new ArrayList<>();
}

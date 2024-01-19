package song.mall2.domain.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionApi {
    private boolean success;
    private String type;
    private String message;
}

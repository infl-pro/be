package song.mall2.domain.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseApi<T> {
    private boolean success;
    private int status;
    private String type;
    private String message;
    private T data;

    public ResponseApi(int status, Class<? extends Exception> type, String message) {
        this.success = false;
        this.status = status;
        this.type = type.getSimpleName();
        this.message = message;
        this.data = null;
    }

    public ResponseApi(int status, String message, T data) {
        this.success = true;
        this.status = status;
        this.type = null;
        this.message = message;
        this.data = data;
    }
}

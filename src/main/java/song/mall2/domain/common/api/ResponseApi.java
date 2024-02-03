package song.mall2.domain.common.api;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseApi<T, M> {
    private boolean success;
    private int status;
    private String type;
    private M message;
    private T data;

    public ResponseApi(int status, Class<? extends Exception> type, M message) {
        this.success = false;
        this.status = status;
        this.type = type.getSimpleName();
        this.message = message;
        this.data = null;
    }

    public ResponseApi(int status, M message, T data) {
        this.success = true;
        this.status = status;
        this.type = null;
        this.message = message;
        this.data = data;
    }
}

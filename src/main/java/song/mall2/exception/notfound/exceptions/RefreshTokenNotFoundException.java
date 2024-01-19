package song.mall2.exception.notfound.exceptions;

import song.mall2.exception.notfound.NotFoundException;

public class RefreshTokenNotFoundException extends NotFoundException {
    public RefreshTokenNotFoundException() {
        super("Refresh Token Not Found Exception");
    }

    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}

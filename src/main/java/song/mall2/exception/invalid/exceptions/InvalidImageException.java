package song.mall2.exception.invalid.exceptions;

import song.mall2.exception.invalid.InvalidException;

public class InvalidImageException extends InvalidException {
    public InvalidImageException() {
        super("Invalid Image Exception");
    }

    public InvalidImageException(String message) {
        super(message);
    }
}

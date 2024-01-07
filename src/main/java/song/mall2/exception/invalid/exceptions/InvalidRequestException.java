package song.mall2.exception.invalid.exceptions;

import song.mall2.exception.invalid.InvalidException;

public class InvalidRequestException extends InvalidException {
    public InvalidRequestException() {
        super("Invalid Request Exception");
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}

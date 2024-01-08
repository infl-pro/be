package song.mall2.exception.invalid.exceptions;

public class InvalidTokenException extends InvalidRequestException {
    public InvalidTokenException() {
        super("Invalid Token Exception");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}

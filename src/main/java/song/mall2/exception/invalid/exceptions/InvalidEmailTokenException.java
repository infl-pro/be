package song.mall2.exception.invalid.exceptions;

public class InvalidEmailTokenException extends InvalidRequestException {
    public InvalidEmailTokenException() {
        super("Invalid Email Token Exception");
    }

    public InvalidEmailTokenException(String message) {
        super(message);
    }
}

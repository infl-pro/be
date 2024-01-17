package song.mall2.exception.invalid.exceptions;

public class InvalidPasswordException extends InvalidRequestException {
    public InvalidPasswordException() {
        super("Invalid Password Exception");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}

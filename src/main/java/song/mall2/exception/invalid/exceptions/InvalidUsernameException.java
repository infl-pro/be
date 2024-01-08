package song.mall2.exception.invalid.exceptions;

public class InvalidUsernameException extends InvalidRequestException {
    public InvalidUsernameException() {
        super("Invalid Username Exception");
    }

    public InvalidUsernameException(String message) {
        super(message);
    }
}

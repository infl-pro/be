package song.mall2.exception.invalid.exceptions;

public class InvalidJwtException extends InvalidRequestException {
    public InvalidJwtException() {
        super("Invalid Jwt Exception");
    }

    public InvalidJwtException(String message) {
        super(message);
    }
}

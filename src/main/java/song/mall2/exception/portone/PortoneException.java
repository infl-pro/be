package song.mall2.exception.portone;

public class PortoneException extends RuntimeException {
    public PortoneException() {
        super("Portone Exception");
    }

    public PortoneException(String message) {
        super(message);
    }
}

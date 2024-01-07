package song.mall2.exception.notfound;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Not Found Exception");
    }

    public NotFoundException(String message) {
        super(message);
    }
}

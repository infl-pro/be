package song.mall2.exception.already;

public class AlreadyException extends RuntimeException {
    public AlreadyException() {
        super("Already Exception");
    }

    public AlreadyException(String message) {
        super(message);
    }
}

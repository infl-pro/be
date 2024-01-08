package song.mall2.exception.illegal;

public class IllegalException extends RuntimeException {
    public IllegalException() {
        super("Illegal Exception");
    }

    public IllegalException(String message) {
        super(message);
    }
}

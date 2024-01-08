package song.mall2.exception.illegal.exceptions;

import song.mall2.exception.illegal.IllegalException;

public class IllegalTokenException extends IllegalException {
    public IllegalTokenException() {
        super("Illegal Token Exception");
    }

    public IllegalTokenException(String message) {
        super(message);
    }
}

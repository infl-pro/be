package song.mall2.exception.invalid.exceptions;

import song.mall2.exception.invalid.InvalidException;

public class InvalidPortonePaymentException extends InvalidException {
    public InvalidPortonePaymentException() {
        super("Invalid Portone Payment Exception");
    }

    public InvalidPortonePaymentException(String message) {
        super(message);
    }
}

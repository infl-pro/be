package song.mall2.exception.portone.exceptions;

import song.mall2.exception.portone.PortoneException;

public class PortonePaymentException extends PortoneException {
    public PortonePaymentException() {
        super("Portone Payment Exception");
    }

    public PortonePaymentException(String message) {
        super(message);
    }
}

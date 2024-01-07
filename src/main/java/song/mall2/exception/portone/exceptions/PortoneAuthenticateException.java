package song.mall2.exception.portone.exceptions;

import song.mall2.exception.portone.PortoneException;

public class PortoneAuthenticateException extends PortoneException {
    public PortoneAuthenticateException() {
        super("Portone Authenticate Exception");
    }

    public PortoneAuthenticateException(String message) {
        super(message);
    }
}

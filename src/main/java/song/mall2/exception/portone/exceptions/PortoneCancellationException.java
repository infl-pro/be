package song.mall2.exception.portone.exceptions;

import song.mall2.exception.portone.PortoneException;

public class PortoneCancellationException extends PortoneException {
    public PortoneCancellationException() {
        super("Portone Cancellation Exception");
    }

    public PortoneCancellationException(String message) {
        super(message);
    }
}

package song.mall2.exception.already.exceptions;

import song.mall2.exception.already.AlreadyException;

public class AlreadyCancelledException extends AlreadyException {
    public AlreadyCancelledException() {
        super("Already Cancelled Exception");
    }

    public AlreadyCancelledException(String message) {
        super(message);
    }
}

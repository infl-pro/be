package song.mall2.exception.notfound.exceptions;

import song.mall2.exception.notfound.NotFoundException;

public class EmailTokenNotFoundException extends NotFoundException {
    public EmailTokenNotFoundException() {
        super("Email Token Not Found Exception");
    }

    public EmailTokenNotFoundException(String message) {
        super(message);
    }
}

package song.mall2.exception.notfound.exceptions;

import song.mall2.exception.notfound.NotFoundException;

public class TokenNotFoundException extends NotFoundException {
    public TokenNotFoundException() {
        super("Token Not Found Exception");
    }

    public TokenNotFoundException(String message) {
        super(message);
    }
}

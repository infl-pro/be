package song.mall2.exception.notfound.exceptions;

import song.mall2.exception.notfound.NotFoundException;

public class CartNotFoundException extends NotFoundException {
    public CartNotFoundException() {
        super("Cart Not Found Exception");
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}

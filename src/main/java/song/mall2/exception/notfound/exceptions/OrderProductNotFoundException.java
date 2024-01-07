package song.mall2.exception.notfound.exceptions;

import song.mall2.exception.notfound.NotFoundException;

public class OrderProductNotFoundException extends NotFoundException {
    public OrderProductNotFoundException() {
        super("Order Product Not Found Exception");
    }

    public OrderProductNotFoundException(String message) {
        super(message);
    }
}

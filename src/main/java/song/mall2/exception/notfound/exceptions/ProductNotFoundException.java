package song.mall2.exception.notfound.exceptions;

import song.mall2.exception.notfound.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {
        super("Product Not Found Exception");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}

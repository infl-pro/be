package song.mall2.exception.invalid.exceptions;

import song.mall2.exception.invalid.InvalidException;

public class InvalidStockQuantityException extends InvalidException {
    public InvalidStockQuantityException() {
        super("Invalid Stock Quantity Exception");
    }

    public InvalidStockQuantityException(String message) {
        super(message);
    }
}

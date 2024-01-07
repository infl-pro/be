package song.mall2.exception.notfound.exceptions;

import song.mall2.exception.notfound.NotFoundException;

public class PaymentNotFoundException extends NotFoundException {
    public PaymentNotFoundException() {
        super("Payment Not Found Exception");
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }
}

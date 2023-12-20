package song.mall2.exception.notfound.exceptions;

import song.mall2.exception.notfound.NotFoundException;

public class OrdersNotFoundException extends NotFoundException {
    public OrdersNotFoundException() {
        super("Orders Not Found Exception");
    }

    public OrdersNotFoundException(String message) {
        super(message);
    }
}

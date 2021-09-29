package com.checkout;

public class CheckoutException extends RuntimeException {

    public CheckoutException(final String message) {
        super(message);
    }

    public CheckoutException(final Throwable cause) {
        super(cause);
    }

    public CheckoutException(final String message, final Throwable cause) {
        super(message, cause);
    }

}

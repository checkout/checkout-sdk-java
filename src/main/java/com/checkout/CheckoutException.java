package com.checkout;

public class CheckoutException extends RuntimeException {

    public CheckoutException(final String message) {
        super(message);
    }

    public CheckoutException(final Throwable cause) {
        super(cause);
    }
}
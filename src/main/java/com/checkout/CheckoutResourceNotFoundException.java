package com.checkout;

public class CheckoutResourceNotFoundException extends CheckoutApiException {
    public CheckoutResourceNotFoundException(String requestId) {
        super(404, requestId);
    }
}
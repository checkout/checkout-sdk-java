package com.checkout;

import static java.lang.String.format;

public class CheckoutAuthorizationException extends CheckoutException {

    public CheckoutAuthorizationException(final String message) {
        super(message);
    }

    public static CheckoutAuthorizationException invalidAuthorization(final SdkAuthorizationType authorizationType) {
        return new CheckoutAuthorizationException(format("Operation requires %s authorization type", authorizationType));
    }

}

package com.checkout;

import static java.lang.String.format;

/**
 * Raised when the credentials cannot authorize a request: the operation needs another authorization type, or the
 * OAuth token request fails or returns an invalid token response.
 */
public final class CheckoutAuthorizationException extends CheckoutException {

    /**
     * Creates the exception.
     *
     * @param message the error message
     */
    public CheckoutAuthorizationException(final String message) {
        super(message);
    }

    /**
     * Creates the exception with the failure that caused it.
     *
     * @param message the error message
     * @param cause   the underlying failure, for example the I/O error of the token request
     */
    public CheckoutAuthorizationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * The exception for an operation that needs an authorization type the credentials do not provide.
     *
     * @param authorizationType the authorization type the operation needs
     * @return the exception
     */
    public static CheckoutAuthorizationException invalidAuthorization(final SdkAuthorizationType authorizationType) {
        return new CheckoutAuthorizationException(format("Operation requires %s authorization type", authorizationType));
    }

}

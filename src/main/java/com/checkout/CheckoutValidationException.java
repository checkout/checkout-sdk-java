package com.checkout;

import com.checkout.common.ErrorResponse;

import java.util.Collections;

public class CheckoutValidationException extends CheckoutApiException {
    private ErrorResponse error;

    public CheckoutValidationException(ErrorResponse error, int httpStatusCode, String requestId) {
        super(httpStatusCode, requestId, generateDetailsMessage(error));
        this.error = error;
    }

    private static String generateDetailsMessage(ErrorResponse error) {
        if (error.getErrorCodes() == null) {
            error.setErrorCodes(Collections.emptyList());
        }
        return "A validation error of type " + error.getErrorType() + " occurred with error codes [" + String.join(",", error.getErrorCodes()) + "].";
    }

    public ErrorResponse getError() {
        return error;
    }
}
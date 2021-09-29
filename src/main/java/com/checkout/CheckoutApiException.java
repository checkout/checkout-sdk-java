package com.checkout;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public final class CheckoutApiException extends CheckoutException {

    private final String requestId;
    private final int httpStatusCode;
    private final Map<String, Object> errorDetails;

    public CheckoutApiException(final String requestId, final int statusCode, final Map<String, Object> errorDetails) {
        super("The API response status code (" + statusCode + ") does not indicate success.");
        this.requestId = requestId;
        this.httpStatusCode = statusCode;
        this.errorDetails = errorDetails;
    }

}

package com.checkout;

import com.checkout.common.CheckoutUtils;

public class CheckoutApiException extends CheckoutException {
    private final int httpStatusCode;
    private final String requestId;

    public CheckoutApiException(int httpStatusCode, String requestId) {
        this(httpStatusCode, requestId, null);
    }

    public CheckoutApiException(int httpStatusCode, String requestId, String additionalInformation) {
        super(generateMessage(httpStatusCode, additionalInformation));
        this.httpStatusCode = httpStatusCode;
        this.requestId = requestId;
    }

    private static String generateMessage(int httpStatusCode, String additionalInformation) {
        String message = "The API response status code (" + httpStatusCode + ") does not indicate success.";

        if (!CheckoutUtils.isNullOrWhitespace(additionalInformation))
            return message + " " + additionalInformation;

        return message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getRequestId() {
        return requestId;
    }
}
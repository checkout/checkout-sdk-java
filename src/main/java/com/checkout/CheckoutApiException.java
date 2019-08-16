package com.checkout;

import com.checkout.common.ApiResponseInfo;
import com.checkout.common.CheckoutUtils;

public class CheckoutApiException extends CheckoutException {
    private final ApiResponseInfo apiResponseInfo;

    public CheckoutApiException(ApiResponseInfo apiResponseInfo) {
        this(apiResponseInfo, null);
    }

    public CheckoutApiException(ApiResponseInfo apiResponseInfo, String additionalInformation) {
        super(generateMessage(apiResponseInfo.getHttpStatusCode(), additionalInformation));
        this.apiResponseInfo = apiResponseInfo;
    }

    public ApiResponseInfo getApiResponseInfo() {
        return apiResponseInfo;
    }

    private static String generateMessage(int httpStatusCode, String additionalInformation) {
        String message = "The API response status code (" + httpStatusCode + ") does not indicate success.";

        if (!CheckoutUtils.isNullOrWhitespace(additionalInformation))
            return message + " " + additionalInformation;

        return message;
    }
}
package com.checkout;

import com.checkout.common.ApiResponseInfo;
import org.apache.commons.lang3.StringUtils;

public class CheckoutApiException extends CheckoutException {

    private final ApiResponseInfo apiResponseInfo;

    public CheckoutApiException(final ApiResponseInfo apiResponseInfo) {
        this(apiResponseInfo, null);
    }

    public CheckoutApiException(final ApiResponseInfo apiResponseInfo, final String additionalInformation) {
        super(generateMessage(apiResponseInfo.getHttpStatusCode(), additionalInformation));
        this.apiResponseInfo = apiResponseInfo;
    }

    public ApiResponseInfo getApiResponseInfo() {
        return apiResponseInfo;
    }

    private static String generateMessage(final int httpStatusCode, final String additionalInformation) {
        final String message = "The API response status code (" + httpStatusCode + ") does not indicate success.";
        if (!StringUtils.isBlank(additionalInformation)) {
            return message + " " + additionalInformation;
        }
        return message;
    }

}


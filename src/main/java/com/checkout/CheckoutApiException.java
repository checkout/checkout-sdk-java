package com.checkout;

import com.checkout.common.CheckoutUtils;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public final class CheckoutApiException extends CheckoutException {

    private final Integer httpStatusCode;
    private final Map<String, String> responseHeaders;
    private final Map<String, Object> errorDetails;

    public CheckoutApiException(final Integer httpStatusCode, final Map<String, String> responseHeaders, final Map<String, Object> errorDetails) {
        super("The API response status code (" + httpStatusCode + ") does not indicate success.");
        this.responseHeaders = responseHeaders;
        this.httpStatusCode = httpStatusCode;
        this.errorDetails = errorDetails;
    }

    public String getRequestId() {
        return CheckoutUtils.getRequestId(responseHeaders);
    }

}

package com.checkout;

import com.checkout.common.ApiResponseInfo;

public class CheckoutResourceNotFoundException extends CheckoutApiException {
    public CheckoutResourceNotFoundException(String requestId) {
        super(new ApiResponseInfo(404, requestId));
    }
}
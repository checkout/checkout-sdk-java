package com.checkout;

import com.checkout.common.CheckoutUtils;
import lombok.Data;

import java.util.Map;

@Data
public class HttpMetadata {

    private Integer httpStatusCode;

    private String body;

    private Map<String, String> responseHeaders;

    public String getRequestId() {
        return CheckoutUtils.getRequestId(responseHeaders);
    }

}

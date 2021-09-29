package com.checkout.common;

import com.checkout.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ApiResponseInfo {

    private int httpStatusCode;

    private String requestId;

    private String body;

    public static ApiResponseInfo fromResponse(final Response response) {
        return new ApiResponseInfo(response.getStatusCode(), response.getRequestId(), response.getBody());
    }

}

package com.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Response {

    private final int statusCode;
    private final String body;
    private final String requestId;

}

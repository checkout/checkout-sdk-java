package com.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class Response {

    private int statusCode;

    private String body;

    private String requestId;

}

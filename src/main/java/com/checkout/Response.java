package com.checkout;

import lombok.Data;

@Data
public class Response {

    private final int statusCode;
    private final String body;
    private final String requestId;

}

package com.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
final class Response {

    private Integer statusCode;

    private String body;

    private Map<String, String> headers;

}

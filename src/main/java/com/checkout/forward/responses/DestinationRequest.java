package com.checkout.forward.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public final class DestinationRequest {

    /**
     * The URL of the forward request. (Required)
     */
    private String url;

    /**
     * The HTTP method of the forward request. (Required)
     */
    private String method;

    /**
     * The HTTP headers of the forward request. Encrypted and sensitive header values are redacted. (Required)
     */
    @Builder.Default
    private Map<String, String> headers = new HashMap<>();

    /**
     * The HTTP message body of the forward request. This is the original value used to initiate the request,
     * with placeholder value text included. For example, {{card_number}} is not replaced with an actual card number. (Required)
     */
    private String body;

}

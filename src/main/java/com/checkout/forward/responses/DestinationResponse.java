package com.checkout.forward.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public final class DestinationResponse {

    /**
     * The HTTP status code of the destination response (Required)
     */
    private int status;

    /**
     * The destination response's HTTP headers. (Required)
     */
    @Builder.Default
    private Map<String, List<String>> headers = new HashMap<>();

    /**
     * The destination response's HTTP message body (Required)
     */
    private String body;

}

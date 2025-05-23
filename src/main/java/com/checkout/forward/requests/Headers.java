package com.checkout.forward.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Headers {

    /**
     * The raw headers to include in the forward request (Required, max 16 characters)
     */
    @Builder.Default
    private Map<String, String> raw = new HashMap<>();

    /**
     * The encrypted headers to include in the forward request, as a JSON object with string values encrypted with JSON
     * Web Encryption (JWE) (Optional, max 8192 characters)
     */
    private String encrypted;

}

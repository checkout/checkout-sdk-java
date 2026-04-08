package com.checkout;

import java.util.Map;

/**
 * Common interface for custom HTTP headers that can be passed alongside API requests.
 * Implementations provide endpoint-specific headers (e.g., Agentic Commerce signatures,
 * authentication session headers).
 */
public interface IHeaders {

    Map<String, String> getHeaders();
}

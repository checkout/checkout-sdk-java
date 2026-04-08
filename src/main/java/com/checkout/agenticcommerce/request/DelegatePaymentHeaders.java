package com.checkout.agenticcommerce.request;

import com.checkout.IHeaders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Custom HTTP headers required by the Agentic Commerce delegate payment endpoint.
 *
 * @see <a href="https://api-reference.checkout.com/">POST /agentic_commerce/delegate_payment</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelegatePaymentHeaders implements IHeaders {

    private String signature;

    private String timestamp;

    @Builder.Default
    private String apiVersion = null;

    @Override
    public Map<String, String> getHeaders() {
        final Map<String, String> headers = new LinkedHashMap<>();
        if (signature != null) {
            headers.put("Signature", signature);
        }
        if (timestamp != null) {
            headers.put("Timestamp", timestamp);
        }
        if (apiVersion != null) {
            headers.put("API-Version", apiVersion);
        }
        return headers;
    }
}

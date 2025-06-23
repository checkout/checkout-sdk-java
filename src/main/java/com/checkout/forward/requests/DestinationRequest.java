package com.checkout.forward.requests;

import com.checkout.forward.requests.signatures.AbstractSignature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DestinationRequest {

    /**
     * The URL to forward the request to (Required, max 1024 characters)
     */
    private String url;
    /**
     * The HTTP method to use for the forward request (Required)
     */
    private MethodType method;
    /**
     * The HTTP headers to include in the forward request (Required)
     */
    private Headers headers;
    /**
     * The HTTP message body to include in the forward request. If you provide source.id or source.token, you can specify
     * placeholder values in the body. The request will be enriched with the respective payment details from the token or
     * payment instrument you specified. For example, {{card_number}} (Required, max 16384 characters)
     */
    private String body;

    /**
     * Optional configuration to add a signature to the forwarded HTTP request. (Optional)
     */
    private AbstractSignature signature;

}

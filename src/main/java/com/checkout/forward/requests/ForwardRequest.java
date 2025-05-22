package com.checkout.forward.requests;

import com.checkout.forward.requests.sources.AbstractSource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ForwardRequest {

    /**
     * The payment source to enrich the forward request with. You can provide placeholder values in
     * destination_request.body. The request will be enriched with the respective payment credentials from the token or
     * payment instrument you specified. For example, {{card_number}} (Required)
     */
    private AbstractSource source;

    /**
     * The parameters of the forward request (Required)
     */
    private DestinationRequest destinationRequest;

    /**
     * Unique reference for the forward request. (Optional, max 80 characters)
     */
    private String reference;

    /**
     * The processing channel ID to associate the billing for the forward request with (Optional,
     * pattern ^(pc)_(\w{26})$)
     */
    private String processingChannelId;

    /**
     * Specifies if and how a network token should be used in the forward request. (Optional)
     */
    private NetworkToken networkToken;

}

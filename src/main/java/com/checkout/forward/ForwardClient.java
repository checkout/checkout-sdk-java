package com.checkout.forward;

import com.checkout.forward.requests.ForwardRequest;
import com.checkout.forward.responses.ForwardAnApiResponse;
import com.checkout.forward.responses.GetForwardResponse;

import java.util.concurrent.CompletableFuture;

public interface ForwardClient {

    /**
     * Forward an API request
     * <b>Beta</b>
     * Forwards an API request to a third-party endpoint.
     * For example, you can forward payment credentials you've stored in our Vault to a third-party payment processor.
     */
    CompletableFuture<ForwardAnApiResponse> forwardAnApiRequest(ForwardRequest forwardRequest);

    /**
     * Get forward request
     * Retrieve the details of a successfully forwarded API request.
     * The details can be retrieved for up to 14 days after the request was initiated.
     */
    CompletableFuture<GetForwardResponse> getForwardRequest(String forwardId);

    // Synchronous methods
    ForwardAnApiResponse forwardAnApiRequestSync(ForwardRequest forwardRequest);
    
    GetForwardResponse getForwardRequestSync(String forwardId);

}

package com.checkout.forward;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.forward.requests.ForwardRequest;
import com.checkout.forward.responses.ForwardAnApiResponse;
import com.checkout.forward.responses.GetForwardResponse;

import java.util.concurrent.CompletableFuture;

public class ForwardClientImpl extends AbstractClient implements ForwardClient {

    private static final String FORWARD_PATH = "forward";

    public ForwardClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<ForwardAnApiResponse> forwardAnApiRequest(final ForwardRequest forwardRequest) {
        validateForwardRequest(forwardRequest);
        return apiClient.postAsync(FORWARD_PATH, sdkAuthorization(), ForwardAnApiResponse.class, forwardRequest, null);
    }

    @Override
    public CompletableFuture<GetForwardResponse> getForwardRequest(final String forwardId) {
        validateForwardId(forwardId);
        return apiClient.getAsync(buildPath(FORWARD_PATH, forwardId), sdkAuthorization(), GetForwardResponse.class);
    }

    // Synchronous methods
    @Override
    public ForwardAnApiResponse forwardAnApiRequestSync(final ForwardRequest forwardRequest) {
        validateForwardRequest(forwardRequest);
        return apiClient.post(FORWARD_PATH, sdkAuthorization(), ForwardAnApiResponse.class, forwardRequest, null);
    }

    @Override
    public GetForwardResponse getForwardRequestSync(final String forwardId) {
        validateForwardId(forwardId);
        return apiClient.get(buildPath(FORWARD_PATH, forwardId), sdkAuthorization(), GetForwardResponse.class);
    }

    // Common methods
    private void validateForwardId(final String forwardId) {
        CheckoutUtils.validateParams("forwardId", forwardId);
    }

    private void validateForwardRequest(final ForwardRequest forwardRequest) {
        CheckoutUtils.validateParams("forwardRequest", forwardRequest);
    }
}

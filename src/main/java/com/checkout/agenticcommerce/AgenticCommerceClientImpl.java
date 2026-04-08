package com.checkout.agenticcommerce;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.agenticcommerce.request.DelegatePaymentRequest;
import com.checkout.agenticcommerce.response.DelegatePaymentResponse;
import com.checkout.common.CheckoutUtils;

import java.util.concurrent.CompletableFuture;

public class AgenticCommerceClientImpl extends AbstractClient implements AgenticCommerceClient {

    private static final String AGENTIC_COMMERCE_PATH = "agentic_commerce";
    private static final String DELEGATE_PAYMENT_PATH = "delegate_payment";

    public AgenticCommerceClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<DelegatePaymentResponse> delegatePayment(final DelegatePaymentRequest request) {
        CheckoutUtils.validateParams("request", request);
        return apiClient.postAsync(
                buildPath(AGENTIC_COMMERCE_PATH, DELEGATE_PAYMENT_PATH),
                sdkAuthorization(),
                DelegatePaymentResponse.class,
                request,
                null);
    }

    @Override
    public DelegatePaymentResponse delegatePaymentSync(final DelegatePaymentRequest request) {
        CheckoutUtils.validateParams("request", request);
        return apiClient.post(
                buildPath(AGENTIC_COMMERCE_PATH, DELEGATE_PAYMENT_PATH),
                sdkAuthorization(),
                DelegatePaymentResponse.class,
                request,
                null);
    }
}

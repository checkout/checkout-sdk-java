package com.checkout.agenticcommerce;

import com.checkout.agenticcommerce.request.DelegatePaymentHeaders;
import com.checkout.agenticcommerce.request.DelegatePaymentRequest;
import com.checkout.agenticcommerce.response.DelegatePaymentResponse;

import java.util.concurrent.CompletableFuture;

public interface AgenticCommerceClient {

    CompletableFuture<DelegatePaymentResponse> delegatePayment(DelegatePaymentRequest request, DelegatePaymentHeaders headers);

    DelegatePaymentResponse delegatePaymentSync(DelegatePaymentRequest request, DelegatePaymentHeaders headers);
}

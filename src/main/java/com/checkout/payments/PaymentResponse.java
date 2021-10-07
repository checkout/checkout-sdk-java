package com.checkout.payments;

import lombok.Data;

@Data
public final class PaymentResponse {

    private PaymentProcessed payment;
    private PaymentPending pending;

    public static PaymentResponse from(final PaymentPending pendingResponse) {
        final PaymentResponse response = new PaymentResponse();
        response.pending = pendingResponse;
        return response;
    }

    public static PaymentResponse from(final PaymentProcessed processedResponse) {
        final PaymentResponse response = new PaymentResponse();
        response.payment = processedResponse;
        return response;
    }

    public boolean isPending() {
        return pending != null;
    }

}
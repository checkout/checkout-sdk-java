package com.checkout.payments;

import lombok.Data;

@Data
public class PaymentResponse {
    private PaymentProcessed payment;
    private PaymentPending pending;

    public static PaymentResponse from(PaymentPending pendingResponse) {
        PaymentResponse response = new PaymentResponse();
        response.pending = pendingResponse;
        return response;
    }

    public static PaymentResponse from(PaymentProcessed processedResponse) {
        PaymentResponse response = new PaymentResponse();
        response.payment = processedResponse;
        return response;
    }

    public boolean isPending() {
        return pending != null;
    }
}
package com.checkout.payments;

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

    public PaymentProcessed getPayment() {
        return payment;
    }

    public void setPayment(PaymentProcessed payment) {
        this.payment = payment;
    }

    public PaymentPending getPending() {
        return pending;
    }

    public boolean isPending() {
        return pending != null;
    }

    public void setPending(PaymentPending pending) {
        this.pending = pending;
    }
}
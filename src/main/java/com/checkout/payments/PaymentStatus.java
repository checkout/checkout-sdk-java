package com.checkout.payments;

public class PaymentStatus {
    public static final String ACTIVE = "Active";
    public static final String AUTHORIZED = "Authorized";
    public static final String CANCELED = "Canceled";
    public static final String CAPTURED = "Captured";
    public static final String DECLINED = "Declined";
    public static final String EXPIRED = "Expired";
    public static final String PARTIALLY_CAPTURED = "Partially Captured";
    public static final String PARTIALLY_REFUNDED = "Partially Refunded";
    public static final String PENDING = "Pending";
    public static final String REFUNDED = "Refunded";
    public static final String VOIDED = "Voided";
    public static final String CARD_VERIFIED = "Card Verified";
    public static final String CHARGEBACK = "Chargeback";
    public static final String PAYMENT_RECEIVED = "Payment Received";

    private PaymentStatus() {}
}
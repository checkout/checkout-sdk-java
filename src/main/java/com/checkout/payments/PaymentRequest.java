package com.checkout.payments;

import com.checkout.common.CheckoutUtils;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class PaymentRequest<T extends RequestSource> {
    private final T source;
    private final T destination;
    private final Long amount;
    @NonNull
    private final String currency;
    private String paymentType;
    private String reference;
    private String description;
    private Boolean capture;
    private CustomerRequest customer;
    private Instant captureOn;
    private BillingDescriptor billingDescriptor;
    private ShippingDetails shipping;
    @SerializedName("3ds")
    private ThreeDSRequest threeDS;
    private String previousPaymentId;
    private RiskRequest risk;
    private String successUrl;
    private String failureUrl;
    private String paymentIp;
    private PaymentRecipient recipient;
    private Processing processing;
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    private PaymentRequest(T sourceOrDestination, String currency, Long amount, boolean isSource) {
        if (sourceOrDestination == null) {
            throw new IllegalArgumentException(String.format("The payment %s is required.", isSource ? "source" : "destination"));
        }
        if (CheckoutUtils.isNullOrWhitespace(currency)) {
            throw new IllegalArgumentException("The currency is required.");
        }
        if (amount != null && amount < 0) {
            throw new IllegalArgumentException("The amount cannot be negative");
        }
        this.source = isSource ? sourceOrDestination : null;
        this.destination = isSource ? null : sourceOrDestination;
        this.amount = amount;
        this.currency = currency;
        this.metadata = new HashMap<>();
    }

    public static <T extends RequestSource> PaymentRequest<T> fromSource(T source, String currency, Long amount) {
        return new PaymentRequest<>(source, currency, amount, true);
    }

    public static <T extends RequestSource> PaymentRequest<T> fromDestination(T destination, String currency, Long amount) {
        return new PaymentRequest<>(destination, currency, amount, false);
    }
}
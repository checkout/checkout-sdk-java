package com.checkout.payments.previous.request;

import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.previous.FundTransferType;
import com.checkout.payments.previous.request.destination.PaymentRequestDestination;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public final class PayoutRequest {

    private PaymentRequestDestination destination;

    private Long amount;

    private FundTransferType fundTransferType;

    private Currency currency;

    private PaymentType paymentType;

    private String reference;

    private String description;

    private Boolean capture;

    private Instant captureOn;

    private CustomerRequest customer;

    private BillingDescriptor billingDescriptor;

    private ShippingDetails shipping;

    private String previousPaymentId;

    private RiskRequest risk;

    private String successUrl;

    private String failureUrl;

    private String paymentIp;

    private PaymentRecipient recipient;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    @Builder.Default
    private Map<String, Object> processing = new HashMap<>();

}

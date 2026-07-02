package com.checkout.payments.previous.request;

import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentRequest {

    private AbstractRequestSource source;

    private Long amount;

    private Currency currency;

    private PaymentType paymentType;

    private Boolean merchantInitiated;

    private String reference;

    private String description;

    private Boolean capture;

    private Instant captureOn;

    private CustomerRequest customer;

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

    private ProcessingSettings processing;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}

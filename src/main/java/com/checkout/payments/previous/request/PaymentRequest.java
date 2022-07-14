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

    @SerializedName("payment_type")
    private PaymentType paymentType;

    @SerializedName("merchant_initiated")
    private Boolean merchantInitiated;

    private String reference;

    private String description;

    private Boolean capture;

    @SerializedName("capture_on")
    private Instant captureOn;

    private CustomerRequest customer;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    private ShippingDetails shipping;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    @SerializedName("previous_payment_id")
    private String previousPaymentId;

    private RiskRequest risk;

    @SerializedName("success_url")
    private String successUrl;

    @SerializedName("failure_url")
    private String failureUrl;

    @SerializedName("payment_ip")
    private String paymentIp;

    private PaymentRecipient recipient;

    private ProcessingSettings processing;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}

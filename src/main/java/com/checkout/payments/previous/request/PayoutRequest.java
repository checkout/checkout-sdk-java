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
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("fund_transfer_type")
    private FundTransferType fundTransferType;

    private Currency currency;

    @SerializedName("payment_type")
    private PaymentType paymentType;

    private String reference;

    private String description;

    private Boolean capture;

    @SerializedName("capture_on")
    private Instant captureOn;

    private CustomerRequest customer;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    private ShippingDetails shipping;

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

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    @Builder.Default
    private Map<String, Object> processing = new HashMap<>();

}

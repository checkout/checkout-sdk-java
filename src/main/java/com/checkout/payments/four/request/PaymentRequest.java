package com.checkout.payments.four.request;

import com.checkout.common.four.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.payments.four.BillingDescriptor;
import com.checkout.payments.four.request.source.RequestSource;
import com.checkout.payments.four.sender.RequestSender;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class PaymentRequest {

    private final RequestSource source;

    private final RequestSender sender;

    private final Long amount;

    private final Currency currency;

    @SerializedName("payment_type")
    private final PaymentType paymentType;

    @SerializedName("merchant_initiated")
    private final boolean merchantInitiated;

    private final String reference;

    private final String description;

    @SerializedName("authorization_type")
    private final AuthorizationType authorizationType;

    private final boolean capture;

    @SerializedName("capture_on")
    private final String captureOn;

    @SerializedName("billing_descriptor")
    private final BillingDescriptor billingDescriptor;

    private final ShippingDetails shipping;

    @SerializedName("3ds")
    private final ThreeDSRequest threeDSRequest;

    @SerializedName("processing_channel_id")
    private final String processingChannelId;

    @SerializedName("previous_payment_id")
    private final String previousPaymentId;

    private final Risk risk;

    private final CustomerRequest customer;

    @SerializedName("success_url")
    private final String successUrl;

    @SerializedName("failure_url")
    private final String failureUrl;

    @SerializedName("payment_ip")
    private final String paymentIp;

    private final PaymentRecipient recipient;

    private final MarketplaceData marketplace;

    private final ProcessingSettings processing;

    private final Map<String, Object> metadata = new HashMap<>();

}

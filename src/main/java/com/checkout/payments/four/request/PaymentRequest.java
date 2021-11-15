package com.checkout.payments.four.request;

import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.MarketplaceData;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.four.AuthorizationType;
import com.checkout.payments.four.BillingDescriptor;
import com.checkout.payments.four.request.source.AbstractRequestSource;
import com.checkout.payments.four.sender.PaymentSender;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
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

    @SerializedName("authorization_type")
    private AuthorizationType authorizationType;

    private Boolean capture;

    @SerializedName("capture_on")
    private String captureOn;

    private CustomerRequest customer;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    private ShippingDetails shipping;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    @SerializedName("previous_payment_id")
    private String previousPaymentId;

    private RiskRequest risk;

    @SerializedName("success_url")
    private String successUrl;

    @SerializedName("failure_url")
    private String failureUrl;

    @SerializedName("payment_ip")
    private String paymentIp;

    private PaymentSender sender;

    private PaymentRecipient recipient;

    private MarketplaceData marketplace;

    private ProcessingSettings processing;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}

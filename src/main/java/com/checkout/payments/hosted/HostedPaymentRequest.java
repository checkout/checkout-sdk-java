package com.checkout.payments.hosted;

import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.MarketplaceData;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Product;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
public final class HostedPaymentRequest {

    private Long amount;

    private Currency currency;

    private String reference;

    private String description;

    private CustomerRequest customer;

    private ShippingDetails shipping;

    private BillingInformation billing;

    private PaymentRecipient recipient;

    private ProcessingSettings processing;

    private List<Product> products;

    private RiskRequest risk;

    @SerializedName("success_url")
    private String successUrl;

    @SerializedName("cancel_url")
    private String cancelUrl;

    @SerializedName("failure_url")
    private String failureUrl;

    private Map<String, Object> metadata;

    private String locale;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    private boolean capture;

    @SerializedName("capture_on")
    private Instant captureOn;

    @SerializedName("payment_type")
    private PaymentType paymentType;

    @SerializedName("payment_ip")
    private String paymentIp;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    @SerializedName("allow_payment_methods")
    private List<PaymentSourceType> allowPaymentMethods;

    // Only available in Four

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    private MarketplaceData marketplace;

}

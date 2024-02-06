package com.checkout.payments.request;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.MarketplaceData;
import com.checkout.payments.Product;
import com.checkout.payments.AuthorizationType;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.checkout.payments.sender.PaymentSender;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public final class PaymentRequest {
    
    @SerializedName("payment_context_id")
    private String paymentContextId;

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
    private Instant captureOn;

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

    /**
     * @deprecated This property will be removed in the future, and should be used
     * {@link PaymentRequest#amountAllocations} instead
     */
    @Deprecated
    private MarketplaceData marketplace;

    @SerializedName("amount_allocations")
    private List<AmountAllocations> amountAllocations;

    private ProcessingSettings processing;

    private List<Product> items;

    private PaymentRetryRequest retry;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    private PaymentSegment segment;

}

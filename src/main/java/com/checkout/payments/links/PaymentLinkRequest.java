package com.checkout.payments.links;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
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
import com.checkout.payments.request.PaymentRetryRequest;
import com.checkout.payments.sender.PaymentSender;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
public final class PaymentLinkRequest {

    private Long amount;

    private Currency currency;

    private String reference;

    private String description;

    @SerializedName("expires_in")
    private Integer expiresIn;

    private CustomerRequest customer;

    private ShippingDetails shipping;

    private BillingInformation billing;

    private PaymentRecipient recipient;

    private ProcessingSettings processing;

    private List<Product> products;

    private Map<String, Object> metadata;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    private RiskRequest risk;

    @SerializedName("return_url")
    private String returnUrl;

    private String locale;

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

    /**
     * Not available on Previous
     */

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("disabled_payment_methods")
    private List<PaymentSourceType> disabledPaymentMethods;

    @SerializedName("customer_retry")
    private PaymentRetryRequest customerRetry;

    private PaymentSender sender;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    @SerializedName("amount_allocations")
    private List<AmountAllocations> amountAllocations;

}

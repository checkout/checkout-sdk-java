package com.checkout.payments.hosted;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Product;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.LocaleType;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.request.PaymentRetryRequest;
import com.checkout.payments.sender.Sender;
import com.checkout.payments.PaymentMethodConfiguration;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
public final class HostedPaymentRequest {

    private Currency currency;

    private BillingInformation billing;

    @SerializedName("success_url")
    private String successUrl;

    @SerializedName("cancel_url")
    private String cancelUrl;

    @SerializedName("failure_url")
    private String failureUrl;

    private Long amount;

    @SerializedName("payment_type")
    private PaymentType paymentType;

    @SerializedName("payment_ip")
    private String paymentIp;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    private String reference;

    private String description;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    @SerializedName("amount_allocations")
    private List<AmountAllocations> amountAllocations;

    private CustomerRequest customer;

    private ShippingDetails shipping;

    private PaymentRecipient recipient;

    private ProcessingSettings processing;

    @SerializedName("allow_payment_methods")
    private List<PaymentSourceType> allowPaymentMethods;

    @SerializedName("disabled_payment_methods")
    private List<PaymentSourceType> disabledPaymentMethods;

    private List<Product> products;

    private RiskRequest risk;

    @SerializedName("customer_retry")
    private PaymentRetryRequest customerRetry;

    private Sender sender;

    private Map<String, Object> metadata;

    private LocaleType locale;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    private Boolean capture;

    @SerializedName("capture_on")
    private Instant captureOn;

    private HostedPaymentInstruction instruction;

    @SerializedName("payment_method_configuration")
    private PaymentMethodConfiguration paymentMethodConfiguration;
}

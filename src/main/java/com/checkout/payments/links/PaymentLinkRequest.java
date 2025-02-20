package com.checkout.payments.links;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Product;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.LocaleType;
import com.checkout.payments.PaymentMethodConfiguration;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.PaymentInstruction;
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

    private BillingInformation billing;

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

    @SerializedName("expires_in")
    private Integer expiresIn;

    private CustomerRequest customer;

    private ShippingDetails shipping;

    private PaymentRecipient recipient;

    private ProcessingSettings processing;

    @SerializedName("allow_payment_methods")
    private List<PaymentSourceType> allowPaymentMethods;

    @SerializedName("disabled_payment_methods")
    private List<PaymentSourceType> disabledPaymentMethods;

    private List<Product> products;

    private Map<String, Object> metadata;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    private RiskRequest risk;

    @SerializedName("customer_retry")
    private PaymentRetryRequest customerRetry;

    private PaymentSender sender;

    @SerializedName("return_url")
    private String returnUrl;

    private LocaleType locale;

    private boolean capture;

    @SerializedName("capture_on")
    private Instant captureOn;

    private PaymentInstruction instruction;

    @SerializedName("payment_method_configuration")
    private PaymentMethodConfiguration paymentMethodConfiguration;
}

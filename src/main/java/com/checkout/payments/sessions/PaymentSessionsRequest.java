package com.checkout.payments.sessions;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.Product;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.request.PaymentCustomerRequest;
import com.checkout.payments.request.PaymentRetryRequest;
import com.checkout.payments.sender.PaymentSender;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSessionsRequest {

    private Long amount;

    private Currency currency;

    @SerializedName("payment_type")
    private PaymentType paymentType;

    private BillingInformation billing;

    @SerializedName("billing_descriptor")
    private BillingDescriptor billingDescriptor;

    private String reference;

    private String description;

    private PaymentCustomerRequest customer;

    private ShippingDetails shipping;

    private PaymentRecipient recipient;

    private ProcessingSettings processing;

    @SerializedName("processing_channel_id")
    private String processingChannelId;

    @SerializedName("expires_on")
    private Instant expiresOn;
    
    @SerializedName("payment_method_configuration")
    private PaymentMethodConfiguration paymentMethodConfiguration;

    @SerializedName("enabled_payment_methods")
    private List<PaymentMethodsType> enabledPaymentMethods;

    @SerializedName("disabled_payment_methods")
    private List<PaymentMethodsType> disabledPaymentMethods;

    private List<Product> items;

    @SerializedName("amount_allocations")
    private List<AmountAllocations> amountAllocations;

    private RiskRequest risk;

    @SerializedName("customer_retry")
    private PaymentRetryRequest customerRetry;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("success_url")
    private String successUrl;

    @SerializedName("failure_url")
    private String failureUrl;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    private String locale;

    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    private PaymentSender sender;

    private Boolean capture;

    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("tax_amount")
    private Integer taxAmount;

}

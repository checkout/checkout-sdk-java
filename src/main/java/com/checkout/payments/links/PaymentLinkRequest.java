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

    /**
     * The payment amount in minor currency units. The exact format depends on the currency.
     * [Required]
     * min 0
     */
    private Long amount;

    /**
     * The three-letter ISO currency code of the payment.
     * [Required]
     * min 3 characters, max 3 characters
     */
    private Currency currency;

    /**
     * The billing information for the payment, including address and phone.
     * [Required]
     */
    private BillingInformation billing;

    /**
     * The type of payment. Must be specified for card payments where the cardholder is not present.
     * [Optional]
     * Enum: "Regular" "Recurring" "MOTO" "Installment" "Unscheduled"
     */
    private PaymentType paymentType;

    /**
     * @deprecated Deprecated in the API on 2025-03-11. Use {@code risk.device.network.ipv4} or {@code risk.device.network.ipv6} instead.
     */
    @Deprecated
    private String paymentIp;

    /**
     * An optional description displayed on the customer's statement identifying a purchase.
     * [Optional]
     */
    private BillingDescriptor billingDescriptor;

    /**
     * A reference you can use to identify the payment, such as an order number.
     * [Optional]
     */
    private String reference;

    /**
     * A description of the payment.
     * [Optional]
     * max 100 characters
     */
    private String description;

    /**
     * The merchant name to display to customers on the checkout page.
     * [Optional]
     */
    private String displayName;

    /**
     * The processing channel to be used for the payment.
     * [Optional]
     * Pattern: ^(pc)_(\w{26})$
     */
    private String processingChannelId;

    /**
     * The amount allocations for marketplace or split payments.
     * [Optional]
     */
    private List<AmountAllocations> amountAllocations;

    /**
     * The time for which the Payment Link remains valid, in seconds.
     * [Optional]
     */
    private Integer expiresIn;

    /**
     * The customer details.
     * [Optional]
     */
    private CustomerRequest customer;

    /**
     * The shipping details.
     * [Optional]
     */
    private ShippingDetails shipping;

    /**
     * The recipient of the payment. Used for financial regulations compliance.
     * [Optional]
     */
    private PaymentRecipient recipient;

    /**
     * Use the processing object to influence or override data during payment processing.
     * [Optional]
     */
    private ProcessingSettings processing;

    /**
     * Specifies which payment method options to present to the customer.
     * [Optional]
     */
    private List<PaymentSourceType> allowPaymentMethods;

    /**
     * Specifies which payment method options to hide from the customer.
     * [Optional]
     */
    private List<PaymentSourceType> disabledPaymentMethods;

    /**
     * The line items or products included in the purchase.
     * [Optional]
     */
    private List<Product> products;

    /**
     * Key-value pairs to store additional information about the transaction.
     * [Optional]
     */
    private Map<String, Object> metadata;

    /**
     * The 3D Secure (3DS) authentication configuration for the payment.
     * [Optional]
     */
    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    /**
     * The risk assessment configuration for the payment.
     * [Optional]
     */
    private RiskRequest risk;

    /**
     * The customer retry configuration for failed payments.
     * [Optional]
     */
    private PaymentRetryRequest customerRetry;

    /**
     * The sender of the payment. Required for financial regulations in some jurisdictions.
     * [Optional]
     */
    private PaymentSender sender;

    /**
     * If provided, the success page will include a button that redirects the customer to this URL.
     * [Optional]
     */
    private String returnUrl;

    /**
     * Creates a translated version of the hosted page in the specified language.
     * [Optional]
     */
    private LocaleType locale;

    /**
     * Whether to capture the payment immediately (if applicable).
     * [Optional]
     */
    private boolean capture;

    /**
     * The date and time at which the payment should be captured (ISO 8601).
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant captureOn;

    /**
     * The payment instruction details, such as purpose of payment.
     * [Optional]
     */
    private PaymentInstruction instruction;

    /**
     * The payment method configuration for stored card and wallet methods.
     * [Optional]
     */
    private PaymentMethodConfiguration paymentMethodConfiguration;
}

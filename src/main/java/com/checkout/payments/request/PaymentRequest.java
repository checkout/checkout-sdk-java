package com.checkout.payments.request;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.MarketplaceData;
import com.checkout.payments.ProductRequest;
import com.checkout.payments.AuthorizationType;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.checkout.payments.PaymentPlan;
import com.checkout.payments.PaymentRouting;
import com.checkout.payments.PaymentSubscription;
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

    /**
     * The unique identifier of a Payment Context. Used to initiate a payment from a context.
     * [Optional]
     */
    private String paymentContextId;

    /**
     * The source of the payment. Discriminated by type.
     * [Optional]
     */
    private AbstractRequestSource source;

    /**
     * The payment amount in minor currency units. To perform a card verification, do not provide
     * the amount or provide a value of 0.
     * [Optional]
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
     * The type of payment. Required for card payments where the cardholder is not present,
     * such as recurring or MOTO payments. For MITs, must not be set to Regular.
     * [Optional]
     * Enum: "Regular" "Recurring" "MOTO" "Installment" "PayLater" "Unscheduled"
     */
    @Builder.Default
    private PaymentType paymentType = PaymentType.REGULAR;

    /**
     * The details of a recurring subscription or installment.
     * [Optional]
     */
    private PaymentPlan paymentPlan;

    /**
     * Whether the payment is a merchant-initiated transaction (MIT).
     * Must be set to true for all MITs. If true, payment_type must not be Regular.
     * [Optional]
     */
    private Boolean merchantInitiated;

    /**
     * A reference you can use to identify the payment, such as an order number.
     * [Optional]
     * max 80 characters
     */
    private String reference;

    /**
     * A description of the payment.
     * [Optional]
     * max 100 characters
     */
    private String description;

    /**
     * Information required to authenticate the payment.
     * [Optional]
     */
    private Authentication authentication;

    /**
     * The authorization type for the payment.
     * [Optional]
     * Enum: "Final" "Estimated"
     */
    private AuthorizationType authorizationType;

    /**
     * The partial authorization configuration for the payment.
     * [Optional]
     */
    private PartialAuthorization partialAuthorization;

    /**
     * Whether to capture the payment immediately (if applicable).
     * [Optional]
     */
    private Boolean capture;

    /**
     * A timestamp (ISO 8601) that determines when the payment should be captured.
     * Providing this field will automatically set capture to true.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant captureOn;

    /**
     * The date and time when the Multibanco payment expires in UTC.
     * [Optional]
     * Format: date-time (ISO 8601)
     */
    private Instant expireOn;

    /**
     * The customer details for the payment.
     * [Optional]
     */
    private CustomerRequest customer;

    /**
     * An optional description displayed on the customer's statement identifying a purchase.
     * [Optional]
     */
    private BillingDescriptor billingDescriptor;

    /**
     * The shipping details for the payment.
     * [Optional]
     */
    private ShippingDetails shipping;

    /**
     * Information required for 3D Secure authentication payments.
     * [Optional]
     */
    @SerializedName("3ds")
    private ThreeDSRequest threeDS;

    /**
     * The processing channel to be used for the payment.
     * [Optional]
     * Pattern: ^(pc)_(\w{26})$
     */
    private String processingChannelId;

    /**
     * An identifier that links the payment to an existing series of payments.
     * Only pass this field for merchant-initiated transactions (MITs) in a recurring payment series.
     * [Optional]
     * max 100 characters
     */
    private String previousPaymentId;

    /**
     * The risk assessment configuration for the payment.
     * [Optional]
     */
    private RiskRequest risk;

    /**
     * For redirect payment methods, overrides the default success redirect URL configured on your account.
     * [Optional]
     * Format: uri
     * max 1024 characters
     */
    private String successUrl;

    /**
     * For redirect payment methods, overrides the default failure redirect URL configured on your account.
     * [Optional]
     * Format: uri
     * max 1024 characters
     */
    private String failureUrl;

    /**
     * @deprecated Deprecated in the API on 2025-03-11. Use {@code risk.device.network.ipv4} or {@code risk.device.network.ipv6} instead.
     */
    @Deprecated
    private String paymentIp;

    /**
     * The sender of the payment. Required for financial regulations in some jurisdictions.
     * [Optional]
     */
    private PaymentSender sender;

    /**
     * The recipient of the payment. Used for financial regulations compliance.
     * [Optional]
     */
    private PaymentRecipient recipient;

    /**
     * @deprecated Use {@link PaymentRequest#amountAllocations} instead.
     */
    @Deprecated
    private MarketplaceData marketplace;

    /**
     * The amount allocations for marketplace or split payments.
     * [Optional]
     */
    private List<AmountAllocations> amountAllocations;

    /**
     * Use the processing object to influence or override data during payment processing.
     * [Optional]
     */
    private ProcessingSettings processing;

    /**
     * The line items or products included in the purchase.
     * [Optional]
     */
    private List<ProductRequest> items;

    /**
     * The retry configuration for failed payment attempts.
     * [Optional]
     */
    private PaymentRetryRequest retry;

    /**
     * The details of the subscription associated with this payment.
     * [Optional]
     */
    private PaymentSubscription subscription;

    /**
     * Key-value pairs to store additional information about the transaction.
     * Supports string, number, and boolean fields. Max 20 fields; each value max 255 characters.
     * [Optional]
     */
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    /**
     * The segment data for the payment, used for analytics and reporting.
     * [Optional]
     */
    private PaymentSegment segment;

    /**
     * The payment instruction details, such as purpose of payment.
     * [Optional]
     */
    private PaymentInstruction instruction;

    /**
     * Controls processor attempts at the payment level.
     * [Optional]
     */
    private PaymentRouting routing;

}

package com.checkout.payments.response;

import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.MarketplaceData;
import com.checkout.common.Resource;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.PanProcessedType;
import com.checkout.payments.PaymentActionSummary;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProductResponse;
import com.checkout.payments.RiskAssessment;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSData;
import com.checkout.payments.request.PaymentInstruction;
import com.checkout.payments.response.destination.PaymentResponseDestination;
import com.checkout.payments.PaymentPlan;
import com.checkout.payments.response.source.ResponseSource;
import com.checkout.payments.sender.Sender;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Response of get payment details (GET /payments/{id}).
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetPaymentResponse extends Resource {

    /**
     * Payment unique identifier.
     * [Required]
     * ^(pay)_(\w{26})$
     * 30 characters
     */
    private String id;

    /**
     * Not part of the get payment details schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private String authenticationId;

    /**
     * Not part of the get payment details schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private String processingChannelId;

    /**
     * The date/time the payment was requested. Read-only UTC timestamp, automatically assigned by Checkout.com.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant requestedOn;

    /**
     * The source of the payment.
     * [Optional]
     */
    private ResponseSource source;

    /**
     * The destination of the payment.
     * [Optional]
     */
    private PaymentResponseDestination destination;

    /**
     * Information about the sender of the payment's funds.
     * [Optional]
     */
    private Sender sender;

    /**
     * The original payment amount.
     * [Required]
     */
    private Long amount;

    /**
     * The full amount from the original authorization, if a partial authorization was requested.
     * [Optional]
     */
    private Long amountRequested;

    /**
     * The three-letter ISO currency code of the payment.
     * [Required]
     * 3 characters
     */
    private Currency currency;

    /**
     * This must be specified for card payments where the cardholder is not present
     * (for example, recurring or mail order / telephone order).
     * [Optional]
     */
    @SerializedName("payment_type")
    private PaymentType type;

    /**
     * The details of a recurring subscription or installment.
     * [Optional]
     */
    private PaymentPlan paymentPlan;

    /**
     * Your reference for the payment.
     * [Optional]
     */
    private String reference;

    /**
     * A description of the payment.
     * [Optional]
     */
    private String description;

    /**
     * Whether the payment was successful.
     * [Required]
     */
    private Boolean approved;

    /**
     * The timestamp (ISO 8601 code) for when the authorization's validity period expires.
     * [Optional]
     */
    private Instant expiresOn;

    /**
     * The status of the payment.
     * [Required]
     */
    private PaymentStatus status;

    /**
     * The payment balances.
     * [Optional]
     */
    private PaymentResponseBalances balances;

    /**
     * Provides information relating to the processing of 3D Secure payments.
     * [Optional]
     */
    @SerializedName("3ds")
    private ThreeDSData threeDSData;

    /**
     * Provides information relating to the authentication of the payment.
     * [Optional]
     */
    private PaymentResponseAuthentication authentication;

    /**
     * Returns the payments risk assessment results.
     * [Optional]
     */
    private RiskAssessment risk;

    /**
     * The customer to which this payment is linked.
     * [Optional]
     */
    private CustomerResponse customer;

    /**
     * An optional description that is displayed on the customer's statement identifying a purchase.
     * [Optional]
     */
    @SerializedName("billing_descriptor")
    private BillingDescriptor billing;

    /**
     * The payment shipping details.
     * [Optional]
     */
    private ShippingDetails shipping;

    /**
     * The IP address used to make the payment. Used by Checkout.com's risk engine to check the customer's
     * IP address. Only accepts IPv4 and IPv6 addresses.
     * [Optional]
     * Format: ipv4
     * max 45 characters
     */
    private String paymentIp;

    /**
     * Not part of the get payment details schema in the API reference.
     * [Optional]
     *
     * @deprecated This property will be removed in the future, and should be used
     * {@link GetPaymentResponse#amountAllocations} instead
     */
    @Deprecated
    private MarketplaceData marketplace;

    /**
     * The sub-entities that the payment is being processed on behalf of.
     * [Optional]
     */
    private List<AmountAllocations> amountAllocations;

    /**
     * Information about the recipient of the payment's funds. Relevant for Account Funding Transactions
     * and VISA or Mastercard domestic UK transactions processed by financial institutions.
     * [Optional]
     */
    private PaymentRecipient recipient;

    /**
     * Returns information related to the processing of the payment.
     * [Optional]
     */
    private ProcessingData processing;

    /**
     * The order's line items.
     * [Optional]
     */
    private List<ProductResponse> items;

    /**
     * A set of key-value pairs that you can attach to a payment. It can be useful for storing additional
     * information in a structured format.
     * [Optional]
     */
    private Map<String, Object> metadata;

    /**
     * The final Electronic Commerce Indicator (ECI) security level used to authorize the payment.
     * Applicable for 3D Secure and network token payments.
     * [Optional]
     */
    private String eci;

    /**
     * The scheme transaction identifier.
     * [Optional]
     */
    private String schemeId;

    /**
     * A summary of the payment's actions, returned when a session ID is used to get the payment details.
     * [Optional]
     */
    private List<PaymentActionSummary> actions;

    /**
     * Configuration relating to asynchronous retries.
     * [Optional]
     */
    private PaymentRetryResponse retry;

    /**
     * Not part of the get payment details schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private PanProcessedType panTypeProcessed;

    /**
     * The flag indicating if Checkout Network Token was available for the payment.
     * [Optional]
     */
    private Boolean ckoNetworkTokenAvailable;

    /**
     * Card payouts only: if the scheme has finished processing the payout request, the UTC date and time
     * the payout was approved or declined.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private String processedOn;

    /**
     * Details about the payment instruction.
     * [Optional]
     */
    private PaymentInstruction instruction;

}

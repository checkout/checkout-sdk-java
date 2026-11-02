package com.checkout.payments.response;

import com.checkout.common.Currency;
import com.checkout.common.CustomerResponse;
import com.checkout.common.Resource;
import com.checkout.payments.PaymentProcessing;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.PaymentSubscription;
import com.checkout.payments.PaymentType;
import com.checkout.payments.RiskAssessment;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSEnrollment;
import com.checkout.payments.PaymentPlan;
import com.checkout.payments.response.source.ResponseSource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Response of request a payment or payout (POST /payments).
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentResponse extends Resource implements Serializable {

    /**
     * The source of the payment.
     * [Optional]
     */
    private ResponseSource source;

    /**
     * The payment's unique identifier.
     * [Required]
     * ^(pay)_(\w{26})$
     * 30 characters
     */
    private String id;

    /**
     * Not part of the PaymentResponse schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private PaymentType paymentType;

    /**
     * Not part of the PaymentResponse schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private PaymentPlan paymentPlan;

    /**
     * The unique identifier for the action performed against this payment.
     * [Required]
     * ^(act)_(\w{26})$
     * 30 characters
     */
    private String actionId;

    /**
     * The payment amount.
     * [Required]
     */
    private Long amount;

    /**
     * The full amount from the original authorization, if a partial authorization was requested and approved.
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
     * Whether or not the authorization or capture was successful.
     * [Required]
     */
    private boolean approved;

    /**
     * The status of the payment.
     * [Required]
     */
    private PaymentStatus status;

    /**
     * The acquirer authorization code if the payment was authorized.
     * [Optional]
     */
    private String authCode;

    /**
     * The Gateway response code.
     * [Required]
     */
    private String responseCode;

    /**
     * The Gateway response summary.
     * [Optional]
     */
    private String responseSummary;

    /**
     * The timestamp (ISO 8601 code) for when the authorization's validity period expires.
     * [Optional]
     */
    private Instant expiresOn;

    /**
     * Provides 3D Secure enrollment status if the payment was downgraded to non-3D Secure.
     * [Optional]
     */
    @SerializedName("3ds")
    private ThreeDSEnrollment threeDSEnrollment;

    /**
     * Returns the payment's risk assessment results.
     * [Optional]
     */
    private RiskAssessment risk;

    /**
     * The customer associated with the payment, if provided in the request.
     * [Optional]
     */
    private CustomerResponse customer;

    /**
     * The payment balances.
     * [Optional]
     */
    private PaymentResponseBalances balances;

    /**
     * The date/time the payment was processed. Read-only UTC timestamp, automatically assigned by Checkout.com.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant processedOn;

    /**
     * Your reference for the payment.
     * [Optional]
     */
    private String reference;

    /**
     * Returns information related to the processing of the payment.
     * [Optional]
     */
    private PaymentProcessing processing;

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
     * The details of the subscription.
     * [Optional]
     */
    private PaymentSubscription subscription;

    /**
     * Configuration relating to asynchronous retries (Beta).
     * [Optional]
     */
    private PaymentRetryResponse retry;

    /**
     * Not part of the PaymentResponse schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private String paymentIp;

    /**
     * Not part of the PaymentResponse schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private PaymentRecipient recipient;

    /**
     * Not part of the PaymentResponse schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private ShippingDetails shipping;

    /**
     * Not part of the PaymentResponse schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private String description;

    /**
     * Not part of the PaymentResponse schema in the API reference; kept for backward compatibility.
     * [Optional]
     */
    private Map<String, Object> metadata = new HashMap<>();

}
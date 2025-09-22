package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated;

import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.balances.Balances;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.customer.Customer;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.processing.Processing;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.retry.Retry;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.risk.Risk;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.subscription.Subscription;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.threeds.Threeds;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

/**
 * Request a payment or payout Response 201
 * Payment processed successfully
 */
@Getter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public final class RequestAPaymentOrPayoutResponseCreated extends Resource {

    /**
     * The payment's unique identifier
     * [Required]
     */
    private String id;

    /**
     * The unique identifier for the action performed against this payment
     * [Required]
     * ^(act)_(\w{26})$
     * 30 characters
     */
    @SerializedName("action_id")
    private String actionId;

    /**
     * The payment amount.
     * [Required]
     */
    private Integer amount;

    /**
     * The three-letter ISO currency code of the payment
     * [Required]
     * 3 characters
     */
    private Currency currency;

    /**
     * Whether or not the authorization or capture was successful
     * [Required]
     */
    private Boolean approved;

    /**
     * The status of the payment
     * [Required]
     */
    private StatusType status;

    /**
     * The Gateway response code
     * [Required]
     */
    @SerializedName("response_code")
    private String responseCode;

    /**
     * The date/time the payment was processed
     * [Required]
     * <date-time>
     */
    @SerializedName("processed_on")
    private Instant processedOn;

    /**
     * The full amount from the original authorization, if a partial authorization was requested and approved.
     * [Optional]
     */
    @SerializedName("amount_requested")
    private Integer amountRequested;

    /**
     * The acquirer authorization code if the payment was authorized
     * [Optional]
     */
    @SerializedName("auth_code")
    private String authCode;

    /**
     * The Gateway response summary
     * [Optional]
     */
    @SerializedName("response_summary")
    private String responseSummary;

    /**
     * The timestamp (ISO 8601 code) for when the authorization's validity period expires
     * [Optional]
     */
    @SerializedName("expires_on")
    private String expiresOn;

    /**
     * Provides 3D Secure enrollment status if the payment was downgraded to non-3D Secure
     * [Optional]
     */
    @SerializedName("3ds")
    private Threeds threeds;

    /**
     * Returns the payment's risk assessment results
     * [Optional]
     */
    private Risk risk;

    /**
     * The source of the payment
     * [Optional]
     */
    private AbstractSource source;

    /**
     * The customer associated with the payment, if provided in the request
     * [Optional]
     */
    private Customer customer;

    /**
     * The payment balances
     * [Optional]
     */
    private Balances balances;

    /**
     * Your reference for the payment
     * [Optional]
     */
    private String reference;

    /**
     * The details of the subscription.
     * [Optional]
     */
    private Subscription subscription;

    /**
     * Returns information related to the processing of the payment
     * [Optional]
     */
    private Processing processing;

    /**
     * The final Electronic Commerce Indicator (ECI) security level used to authorize the payment. Applicable for 3D
     * Secure and network token payments
     * [Optional]
     */
    private String eci;

    /**
     * The scheme transaction identifier
     * [Optional]
     */
    @SerializedName("scheme_id")
    private String schemeId;

    /**
     * Configuration relating to asynchronous retries
     * [Optional]
     */
    private Retry retry;

}

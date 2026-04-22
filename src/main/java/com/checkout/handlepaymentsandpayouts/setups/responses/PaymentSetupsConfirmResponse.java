package com.checkout.handlepaymentsandpayouts.setups.responses;

import com.checkout.common.Resource;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentStatus;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.threeds.Threeds;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.risk.Risk;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.customer.Customer;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.processing.Processing;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.balances.Balances;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.subscription.Subscription;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.retry.Retry;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.Instant;

/**
 * Payment setup confirmation response
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSetupsConfirmResponse extends Resource {

    /**
     * The payment's unique identifier.
     * [Optional]
     */
    private String id;

    /**
     * The unique identifier for the action performed against this payment.
     * [Optional]
     */
    private String actionId;

    /**
     * The payment amount.
     * [Optional]
     */
    private Long amount;

    /**
     * The three-letter ISO currency code of the payment.
     * [Optional]
     */
    private Currency currency;

    /**
     * Whether or not the authorization or capture was successful.
     * [Optional]
     */
    private Boolean approved;

    /**
     * The status of the payment.
     * [Optional]
     */
    private PaymentStatus status;

    /**
     * The Gateway response code.
     * [Optional]
     */
    private String responseCode;

    /**
     * The date and time at which the payment was processed.
     * [Optional]
     * Format: date-time (ISO 8601)
     */
    private Instant processedOn;

    /**
     * The full amount from the original authorization, if a partial authorization was requested and approved.
     * [Optional]
     */
    private Long amountRequested;

    /**
     * The acquirer authorization code if the payment was authorized.
     * [Optional]
     */
    private String authCode;

    /**
     * The Gateway response summary.
     * [Optional]
     */
    private String responseSummary;

    /**
     * The timestamp for when the authorization's validity period expires.
     * [Optional]
     * Format: date-time (ISO 8601)
     */
    private String expiresOn;

    /**
     * Provides 3D Secure enrollment status if the payment was downgraded to non-3D Secure.
     * [Optional]
     */
    @SerializedName("3ds")
    private Threeds threeDSecure;

    /**
     * Returns the payment's risk assessment results.
     * [Optional]
     */
    private Risk risk;

    /**
     * The source of the payment.
     * [Optional]
     */
    private PaymentSetupSource source;

    /**
     * The customer associated with the payment, if provided in the request.
     * [Optional]
     */
    private Customer customer;

    /**
     * The payment balances.
     * [Optional]
     */
    private Balances balances;

    /**
     * Your reference for the payment.
     * [Optional]
     */
    private String reference;

    /**
     * The details of the subscription.
     * [Optional]
     */
    private Subscription subscription;

    /**
     * Returns information related to the processing of the payment.
     * [Optional]
     */
    private Processing processing;

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
     * The retry information.
     * [Optional]
     */
    private Retry retry;
}

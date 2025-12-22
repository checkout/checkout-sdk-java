package com.checkout.handlepaymentsandpayouts.setups.responses;

import com.checkout.HttpMetadata;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentStatus;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupSource;
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
public class PaymentSetupsConfirmResponse extends HttpMetadata {

    /**
     * The payment's unique identifier
     */
    private String id;

    /**
     * The unique identifier for the action performed against this payment
     */
    @SerializedName("action_id")
    private String actionId;

    /**
     * The payment amount
     */
    private Long amount;

    /**
     * The three-letter ISO currency code of the payment
     */
    private Currency currency;

    /**
     * Whether or not the authorization or capture was successful
     */
    private Boolean approved;

    /**
     * The status of the payment
     */
    private PaymentStatus status;

    /**
     * The Gateway response code
     */
    @SerializedName("response_code")
    private String responseCode;

    /**
     * The date and time at which the payment was processed
     */
    @SerializedName("processed_on")
    private Instant processedOn;

    /**
     * The full amount from the original authorization, if a partial authorization was requested and approved
     */
    @SerializedName("amount_requested")
    private Long amountRequested;

    /**
     * The acquirer authorization code if the payment was authorized
     */
    @SerializedName("auth_code")
    private String authCode;

    /**
     * The Gateway response summary
     */
    @SerializedName("response_summary")
    private String responseSummary;

    /**
     * The timestamp (ISO 8601 code) for when the authorization's validity period expires
     */
    @SerializedName("expires_on")
    private String expiresOn;

    /**
     * Provides 3D Secure enrollment status if the payment was downgraded to non-3D Secure
     */
    @SerializedName("3ds")
    private Threeds threeDSecure;

    /**
     * Returns the payment's risk assessment results
     */
    private Risk risk;

    /**
     * The source of the payment
     */
    private PaymentSetupSource source;

    /**
     * The customer associated with the payment, if provided in the request
     */
    private Customer customer;

    /**
     * The payment balances
     */
    private Balances balances;

    /**
     * Your reference for the payment
     */
    private String reference;

    /**
     * The The details of the subscription
     */
    private Subscription subscription;

    /**
     * Returns information related to the processing of the payment
     */
    private Processing processing;

    /**
     *  The final Electronic Commerce Indicator (ECI) security level used to authorize the payment. 
     *  Applicable for 3D Secure and network token payments
     */
    private String eci;

    /**
     * The scheme transaction identifier
     */
    @SerializedName("scheme_id")
    private String schemeId;

    /**
     * The retry information
     */
    private Retry retry;
}
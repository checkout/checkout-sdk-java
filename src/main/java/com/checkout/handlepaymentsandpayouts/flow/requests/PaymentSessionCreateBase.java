package com.checkout.handlepaymentsandpayouts.flow.requests;

import com.checkout.payments.AuthorizationType;
import com.checkout.payments.LocaleType;
import com.checkout.payments.PaymentPlan;
import com.checkout.payments.PaymentType;
import com.checkout.payments.RiskRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base class for the payment session requests that create a session.
 *
 * The fields declared here are accepted only when a payment session is created, either by
 * Request a Payment Session (POST /payment-sessions) or by Request a Payment Session with
 * Payment (POST /payment-sessions/complete). They are not accepted by Submit a Payment
 * Session (POST /payment-sessions/{id}/submit), which is why PaymentSessionSubmitRequest
 * does not extend this class.
 *
 * The defaults declared here mirror the API defaults, so a request that omits them behaves
 * the same whether or not the SDK sends them.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class PaymentSessionCreateBase extends PaymentSessionInfo {

    /**
     * Specifies whether to capture the payment, if applicable. Default: true
     * [Optional]
     */
    @Builder.Default
    private Boolean capture = true;

    /**
     * Must be specified for card-not-present (CNP) payments. Default: "Regular"
     * [Optional]
     * Enum: "Regular" "Recurring" "MOTO" "Installment" "Unscheduled"
     */
    @Builder.Default
    private PaymentType paymentType = PaymentType.REGULAR;

    /**
     * Creates a translated version of the page in the specified language. Default: "en-GB"
     * [Optional]
     */
    @Builder.Default
    private LocaleType locale = LocaleType.EN_GB;

    /**
     * The authorization type.
     * [Optional]
     * Enum: "Final" "Estimated"
     * Default: "Final"
     */
    private AuthorizationType authorizationType;

    /**
     * A description for the payment.
     * [Optional]
     * max 100 characters
     */
    private String description;

    /**
     * The merchant's display name.
     * [Optional]
     * max 255 characters
     */
    private String displayName;

    /**
     * The information to process a recurring payment request. To be used when the payment_type is Recurring.
     * [Optional]
     */
    private PaymentPlan paymentPlan;

    /**
     * Configures the risk assessment performed during payment processing.
     * [Optional]
     */
    private RiskRequest risk;
}

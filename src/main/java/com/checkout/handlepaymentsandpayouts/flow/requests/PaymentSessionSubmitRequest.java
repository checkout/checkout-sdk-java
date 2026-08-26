package com.checkout.handlepaymentsandpayouts.flow.requests;

import com.checkout.handlepaymentsandpayouts.flow.entities.PaymentMethodConfiguration;
import com.checkout.payments.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Request to submit a payment session.
 *
 * This request works with the Flow handleSubmit callback, where you can perform a customized payment submission.
 * You must send the unmodified response body as the response of the handleSubmit callback.
 *
 * Every field is optional except sessionData. A field you do not set is omitted from the request
 * body, which leaves the value provided when the payment session was created untouched. This is
 * why capture and paymentType carry no default here, unlike on the session creation requests.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class PaymentSessionSubmitRequest extends PaymentSessionInfo {

    /**
     * A unique token representing the additional customer data captured by Flow,
     * as received from the handleSubmit callback.
     * Do not log or store this value.
     * [Required]
     */
    private String sessionData;

    /**
     * Specifies whether to capture the payment, if applicable.
     * Leave this field unset to keep the value provided when the payment session was created.
     * If it was not provided then either, the API applies its default of true.
     * [Optional]
     */
    private Boolean capture;

    /**
     * Must be specified for card-not-present (CNP) payments.
     * Leave this field unset to keep the value provided when the payment session was created.
     * If it was not provided then either, the API applies its default of "Regular".
     * [Optional]
     * Enum: "Regular" "Recurring" "MOTO" "Installment" "Unscheduled"
     */
    private PaymentType paymentType;

    /**
     * Configurations for payment method-specific settings.
     * [Optional]
     */
    private PaymentMethodConfiguration paymentMethodConfiguration;

    /**
     * Deprecated - The Customer's IP address. Only IPv4 and IPv6 addresses are accepted.
     * [Optional]
     */
    @Deprecated
    private String ipAddress;
}

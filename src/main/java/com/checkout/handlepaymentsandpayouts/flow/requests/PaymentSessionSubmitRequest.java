package com.checkout.handlepaymentsandpayouts.flow.requests;

import com.checkout.handlepaymentsandpayouts.flow.entities.PaymentMethodConfiguration;
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
     */
    private String sessionData;

    /**
     * Configurations for payment method-specific settings.
     */
    private PaymentMethodConfiguration paymentMethodConfiguration;

    /**
     * Deprecated - The Customer's IP address. Only IPv4 and IPv6 addresses are accepted.
     */
    @Deprecated
    private String ipAddress;
}
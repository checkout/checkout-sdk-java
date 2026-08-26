package com.checkout.handlepaymentsandpayouts.flow.requests;

import com.checkout.common.PaymentMethodType;
import com.checkout.handlepaymentsandpayouts.flow.entities.CustomerRetry;
import com.checkout.handlepaymentsandpayouts.flow.entities.PaymentMethodConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

/**
 * Request to create a payment session.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class PaymentSessionCreateRequest extends PaymentSessionCreateBase {

    /**
     * A timestamp specifying when the PaymentSession should expire, as an ISO 8601 code.
     * [Optional]
     * Format: date-time (ISO 8601)
     */
    private Instant expiresOn;

    /**
     * Specifies which payment method options to present to the customer.
     * [Optional]
     */
    private List<PaymentMethodType> enabledPaymentMethods;

    /**
     * Specifies which payment method options to not present to the customer.
     * [Optional]
     */
    private List<PaymentMethodType> disabledPaymentMethods;

    /**
     * Configurations for payment method-specific settings.
     * [Optional]
     */
    private PaymentMethodConfiguration paymentMethodConfiguration;

    /**
     * Configuration for asynchronous retries.
     * [Optional]
     */
    private CustomerRetry customerRetry;

    /**
     * Deprecated - The Customer's IP address. Only IPv4 and IPv6 addresses are accepted.
     * [Optional]
     */
    @Deprecated
    private String ipAddress;
}

package com.checkout.handlepaymentsandpayouts.flow.requests;

import com.checkout.common.PaymentMethodType;
import com.checkout.handlepaymentsandpayouts.flow.entities.CustomerRetry;
import com.checkout.handlepaymentsandpayouts.flow.entities.PaymentMethodConfiguration;
import com.google.gson.annotations.SerializedName;
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
public final class PaymentSessionCreateRequest extends PaymentSessionInfo {

    /**
     * A timestamp specifying when the PaymentSession should expire, as an ISO 8601 code.
     */
    private Instant expiresOn;

    /**
     * Specifies which payment method options to present to the customer.
     */
    private List<PaymentMethodType> enabledPaymentMethods;

    /**
     * Specifies which payment method options to not present to the customer.
     */
    private List<PaymentMethodType> disabledPaymentMethods;

    /**
     * Configurations for payment method-specific settings.
     */
    private PaymentMethodConfiguration paymentMethodConfiguration;

    /**
     * Configuration for asynchronous retries.
     */
    private CustomerRetry customerRetry;

    /**
     * Deprecated - The Customer's IP address. Only IPv4 and IPv6 addresses are accepted.
     */
    @Deprecated
    private String ipAddress;
}
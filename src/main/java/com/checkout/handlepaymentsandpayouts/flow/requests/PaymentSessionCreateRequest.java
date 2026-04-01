package com.checkout.handlepaymentsandpayouts.flow.requests;

import com.checkout.common.PaymentMethodType;
import com.checkout.handlepaymentsandpayouts.flow.entities.CustomerRetry;
import com.checkout.handlepaymentsandpayouts.flow.entities.PaymentMethodConfiguration;
import com.checkout.payments.LocaleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
     * Creates a translated version of the page in the specified language. Default: "en-GB"
     */
    @Builder.Default
    private LocaleType locale = LocaleType.EN_GB;

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
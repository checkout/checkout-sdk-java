package com.checkout.handlepaymentsandpayouts.setups.entities.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Settings for the Payment Setup.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Settings {

    /**
     * The URL to redirect the customer to, if the payment is successful.
     * For payment methods with a redirect, this value overrides the default success redirect URL configured on your account.
     * [Optional]
     * Format: uri
     * max 255 characters
     */
    private String successUrl;

    /**
     * The URL to redirect the customer to, if the payment is unsuccessful.
     * For payment methods with a redirect, this value overrides the default failure redirect URL configured on your account.
     * [Optional]
     * Format: uri
     * max 255 characters
     */
    private String failureUrl;

    /**
     * Indicates whether to capture the payment immediately.
     * [Optional]
     * Default: true
     */
    private Boolean capture;

    /**
     * The list of payment methods excluded from the Payment Setup, for example "card", "klarna" or "stcpay".
     * [Optional]
     */
    private List<String> excludedPaymentMethods;
}

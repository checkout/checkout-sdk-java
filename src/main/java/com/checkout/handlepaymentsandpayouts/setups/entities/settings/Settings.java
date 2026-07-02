package com.checkout.handlepaymentsandpayouts.setups.entities.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment setup configuration settings
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Settings {

    /**
     *  &lt;= 255 characters
     * The URL to redirect the customer to, if the payment is successful.
     * For payment methods with a redirect, this value overrides the default success redirect URL configured on your account.
     */
    private String successUrl;

    /**
     *  &lt;= 255 characters
     * The URL to redirect the customer to, if the payment is unsuccessful.
     * For payment methods with a redirect, this value overrides the default failure redirect URL configured on your account.  
     */
    private String failureUrl;
}
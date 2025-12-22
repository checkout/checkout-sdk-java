package com.checkout.handlepaymentsandpayouts.setups.entities.settings;

import com.google.gson.annotations.SerializedName;
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
public class Settings {

    /**
     *  &lt;= 255 characters
     * The URL to redirect the customer to, if the payment is successful.
     * For payment methods with a redirect, this value overrides the default success redirect URL configured on your account.
     */
    @SerializedName("success_url")
    private String successUrl;

    /**
     *  &lt;= 255 characters
     * The URL to redirect the customer to, if the payment is unsuccessful.
     * For payment methods with a redirect, this value overrides the default failure redirect URL configured on your account.  
     */
    @SerializedName("failure_url")
    private String failureUrl;
}
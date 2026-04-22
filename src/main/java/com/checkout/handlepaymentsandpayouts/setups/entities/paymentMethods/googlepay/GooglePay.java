package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.googlepay;

import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.CardAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * The Google Pay payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class GooglePay extends PaymentMethodBase {

    /**
     * The Google Pay encrypted payment token data.
     * [Optional]
     */
    private GooglePayTokenData tokenData;

    /**
     * The Google Pay payment token string.
     * [Optional]
     */
    private String token;

    /**
     * The date and time the token expires.
     * [Optional]
     * Format: date-time (ISO 8601)
     */
    private Instant expiresOn;

    /**
     * Set to true if you intend to reuse the payment credentials in subsequent payments.
     * [Optional]
     */
    private Boolean storeForFutureUse;

    /**
     * The customer's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * The card account holder details.
     * [Optional]
     */
    private CardAccountHolder accountHolder;
}

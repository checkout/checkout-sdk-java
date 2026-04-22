package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card;

import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.CardAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * The Card payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Card extends PaymentMethodBase {

    /**
     * The card number (without separators). Write-only.
     * [Optional]
     */
    private String number;

    /**
     * The last four digits of the card number. Read-only.
     * [Optional]
     */
    private String last4;

    /**
     * The card issuer's Bank Identification Number (BIN). Read-only.
     * [Optional]
     */
    private String bin;

    /**
     * The card scheme. Read-only.
     * [Optional]
     */
    private String scheme;

    /**
     * The expiry month of the card.
     * [Optional]
     */
    private Integer expiryMonth;

    /**
     * The expiry year of the card.
     * [Optional]
     */
    private Integer expiryYear;

    /**
     * The cardholder's name. Write-only.
     * [Optional]
     */
    private String name;

    /**
     * The card verification value/code. 3 digits, except for American Express (4 digits). Write-only.
     * [Optional]
     */
    private String cvv;

    /**
     * Set to true for payments that use stored card details. Write-only.
     * [Optional]
     */
    private Boolean stored;

    /**
     * The time by which the card details must be confirmed. Read-only.
     * [Optional]
     * Format: date-time (ISO 8601)
     */
    private Instant expiresOn;

    /**
     * Set to true if you intend to reuse the payment credentials in subsequent payments. Write-only.
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

    /**
     * Indicates whether to use the Real-Time Account Updater to update the card information.
     * [Optional]
     */
    private Boolean allowUpdate;
}

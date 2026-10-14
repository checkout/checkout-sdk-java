package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The card details of the card payment method.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CardDetails {

    /**
     * The card number (without separators).
     * [Optional] writeOnly
     */
    private String number;

    /**
     * The last four digits of the card number.
     * [Optional] readOnly
     */
    private String last4;

    /**
     * The card issuer's Bank Identification Number (BIN).
     * [Optional] readOnly
     */
    private String bin;

    /**
     * The card scheme.
     * [Optional] readOnly
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
     * The cardholder's name.
     * [Optional] writeOnly
     */
    private String name;

    /**
     * The card verification value/code. 3 digits, except for American Express (4 digits).
     * [Optional] writeOnly
     */
    private String cvv;

    /**
     * Indicates whether this card is being submitted from your own stored card-on-file system, rather
     * than being entered by the customer at the time of payment. Set to true when you provide card
     * details that you already have on file for the customer.
     * [Optional] writeOnly
     */
    private Boolean stored;

    /**
     * The time by which the card details must be confirmed.
     * [Optional] readOnly
     * Format: date-time (ISO 8601)
     */
    private Instant expiresOn;

    /**
     * Indicates whether to use the Real-Time Account Updater to update the card information.
     * [Optional]
     */
    private Boolean allowUpdate;
}

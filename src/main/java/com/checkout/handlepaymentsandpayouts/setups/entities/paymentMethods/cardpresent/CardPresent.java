package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cardpresent;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Card Present payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class CardPresent extends PaymentMethodBase {

    /**
     * The Track 2 data read from card or device.
     * [Optional] writeOnly
     */
    private String track2;

    /**
     * The EMV data read from the card or device.
     * [Optional] writeOnly
     */
    private String emv;

    /**
     * The mode used to capture the card details at the point of sale.
     * [Optional] writeOnly
     */
    private String entryMode;

    /**
     * The encrypted PIN block details.
     * [Optional] writeOnly
     */
    private CardPresentPin pin;

    /**
     * Set to true if you intend to reuse the payment credentials in subsequent payments.
     * [Optional] writeOnly
     */
    private Boolean storeForFutureUse;

    /**
     * The cardholder's name.
     * [Optional] writeOnly
     */
    private String name;
}

package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Checkout.com card token to use for the card payment method.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CardToken {

    /**
     * The Checkout.com card token identifier.
     * [Optional]
     */
    private String id;
}

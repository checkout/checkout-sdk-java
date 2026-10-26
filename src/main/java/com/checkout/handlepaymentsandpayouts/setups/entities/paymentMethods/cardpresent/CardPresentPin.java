package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cardpresent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The encrypted PIN block details of the Card Present payment method.
 *
 * @deprecated card_present is no longer part of the Payment Setups API specification. There is no
 * replacement. Will be removed in the next major version.
 */
@Deprecated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CardPresentPin {

    /**
     * The identifier of the key set used to encrypt the PIN block.
     * [Required] writeOnly
     */
    private String keySetId;

    /**
     * The encrypted PIN block.
     * [Required] writeOnly
     */
    private String block;

    /**
     * The format of the encrypted PIN block.
     * [Required] writeOnly
     */
    private String blockFormat;
}

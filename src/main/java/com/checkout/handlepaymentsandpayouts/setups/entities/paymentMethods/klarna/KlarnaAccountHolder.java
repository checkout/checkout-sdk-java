package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder details returned by Klarna after the shopper completes verification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class KlarnaAccountHolder {

    /**
     * The full name of the account holder.
     * [Optional] readOnly
     */
    private String name;
}

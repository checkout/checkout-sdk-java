package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.multibanco;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Multibanco payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Multibanco extends PaymentMethodBase {

    /**
     * The account holder's name.
     * [Optional]
     * min 3 characters, max 100 characters
     */
    private String accountHolderName;
}

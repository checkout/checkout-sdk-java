package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bancontact;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Bancontact payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Bancontact extends PaymentMethodBase {

    /**
     * The account holder's name.
     * [Optional]
     * min 3 characters, max 100 characters
     */
    private String accountHolderName;
}

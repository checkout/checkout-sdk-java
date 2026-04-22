package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.p24;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The P24 (Przelewy24) payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class P24 extends PaymentMethodBase {

    /**
     * The account holder's details.
     * [Optional]
     */
    private P24AccountHolder accountHolder;
}

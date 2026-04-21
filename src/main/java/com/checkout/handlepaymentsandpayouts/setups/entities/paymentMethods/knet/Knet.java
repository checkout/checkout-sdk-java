package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.knet;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The KNET payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Knet extends PaymentMethodBase {

    /**
     * The customer's preferred language on the issuer's site.
     * [Optional]
     */
    private KnetLanguage language;
}

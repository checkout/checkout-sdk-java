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
     * The customer's preferred language, as an ISO 639-1 code. For example, the language selected on your site,
     * if the issuer's site supports it.
     * The specification marks this optional, but the API keeps the method invalid until it is sent.
     * [Optional]
     */
    private KnetLanguage language;
}

package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Klarna payment method configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Klarna extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. Defaults to disabled.
     * [Optional]
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The next available action. Contains type, client_token, and session_id.
     * [Optional]
     */
    private PaymentMethodAction action;
}

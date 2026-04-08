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
     * <p>[Optional]</p>
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The next available action. Contains type {@code sdk}, {@code client_token}, {@code session_id}.
     * <p>[Read-only]</p>
     */
    private PaymentMethodAction action;
}
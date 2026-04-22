package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.instrument;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The instrument payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Instrument extends PaymentMethodBase {

    /**
     * The unique identifier of the selected instrument.
     * [Optional]
     */
    private String id;

    /**
     * The next available action for the payment method.
     * [Optional]
     */
    private PaymentMethodAction action;
}

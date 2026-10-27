package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tabby;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The Tabby payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Tabby extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. When you create a Payment Setup, this
     * defaults to disabled.
     * [Optional]
     * Default: disabled
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The available payment types for Tabby (for example, installments).
     * [Optional]
     */
    private List<String> paymentTypes;
}

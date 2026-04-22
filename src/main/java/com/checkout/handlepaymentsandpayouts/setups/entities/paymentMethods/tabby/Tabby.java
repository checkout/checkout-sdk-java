package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tabby;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Tabby payment method configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Tabby extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. Defaults to disabled.
     * [Optional]
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The available payment types for Tabby (for example, installments).
     * [Optional]
     */
    private List<String> paymentTypes;
}

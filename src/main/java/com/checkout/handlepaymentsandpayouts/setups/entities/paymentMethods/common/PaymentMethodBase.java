package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;

import java.util.List;

/**
 * Base class for all payment methods with common properties
 */
@Data
public abstract class PaymentMethodBase {

    /**
     * The status of the payment method
     */
    private String status;

    /**
     * Configuration flags for the payment method
     */
    private List<String> flags;

    /**
     * Default: "disabled"
     * The initialization state of the payment method.
     * When you create a Payment Setup, this defaults to disabled.
     * Enum: "disabled" "enabled"
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;
}
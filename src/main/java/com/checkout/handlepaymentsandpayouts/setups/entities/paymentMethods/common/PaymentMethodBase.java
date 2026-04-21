package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import lombok.Data;

import java.util.List;

/**
 * Base class for all payment methods with common properties (status and flags).
 */
@Data
public abstract class PaymentMethodBase {

    /**
     * The payment method status.
     * [Optional]
     */
    private PaymentMethodStatus status;

    /**
     * An array of error codes or indicators that highlight missing or invalid information.
     * [Optional]
     */
    private List<String> flags;
}

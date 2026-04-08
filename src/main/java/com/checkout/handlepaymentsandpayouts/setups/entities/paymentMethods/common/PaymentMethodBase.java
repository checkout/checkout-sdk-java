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
     * <p>[Read-only]</p>
     */
    private PaymentMethodStatus status;

    /**
     * An array of error codes or indicators that highlight missing or invalid information.
     * <p>[Read-only]</p>
     */
    private List<String> flags;
}
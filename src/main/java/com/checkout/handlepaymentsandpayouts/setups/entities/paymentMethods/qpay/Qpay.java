package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.qpay;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The QPay payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Qpay extends PaymentMethodBase {

    /**
     * The Qatari national ID. Must start with 2 or 3, followed by 10 digits.
     * [Optional]
     */
    private String nationalId;

    /**
     * A description of the payment order. Alphanumeric characters only.
     * [Optional]
     * max 255 characters
     */
    private String description;
}

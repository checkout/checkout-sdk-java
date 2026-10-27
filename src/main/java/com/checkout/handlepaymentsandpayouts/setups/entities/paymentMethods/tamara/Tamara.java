package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tamara;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Tamara payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Tamara extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. When you create a Payment Setup, this
     * defaults to disabled.
     * [Optional]
     * Default: disabled
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * Risk signals about the customer, forwarded to Tamara with the payment.
     * [Optional]
     */
    private TamaraRiskAssessment riskAssessment;
}

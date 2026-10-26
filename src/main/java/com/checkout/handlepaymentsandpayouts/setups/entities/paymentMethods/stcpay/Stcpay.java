package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stcpay;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The stc pay payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Stcpay extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. When you create a Payment Setup, this
     * defaults to disabled. On update the API replaces the payment methods, so send enabled again
     * to keep the method enabled.
     * [Optional]
     * Default: disabled
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The one-time password (OTP) for stc pay.
     * [Optional] writeOnly
     */
    private String otp;

    /**
     * The next available action for the payment method. Contains type otp when an OTP is required.
     * [Optional] readOnly
     */
    private PaymentMethodAction action;
}

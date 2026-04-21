package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stcpay;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * STC Pay payment method configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Stcpay extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. Defaults to disabled.
     * [Optional]
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The one-time password (OTP) for STC Pay. Write-only.
     * [Optional]
     */
    private String otp;

    /**
     * The next available action. Contains type otp when an OTP is required.
     * [Optional]
     */
    private PaymentMethodAction action;
}

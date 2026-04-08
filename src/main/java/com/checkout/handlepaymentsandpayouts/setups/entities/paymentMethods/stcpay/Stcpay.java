package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stcpay;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * STC Pay payment method configuration
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Stcpay extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. Defaults to disabled.
     * <p>[Optional]</p>
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The one-time password (OTP) for stc pay.
     * <p>[Write-only]</p>
     */
    private String otp;

    /**
     * The next available action. Contains type {@code otp} when an OTP is required.
     * <p>[Read-only]</p>
     */
    private PaymentMethodAction action;
}
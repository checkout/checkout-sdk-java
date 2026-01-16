package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stcpay;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodOptions;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * STC Pay payment method configuration
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Stcpay extends PaymentMethodBase {

    /**
     * The one-time password (OTP) for STC Pay authentication
     */
    private String otp;

    /**
     * Payment method options specific to STC Pay
     */
    @SerializedName("payment_method_options")
    private PaymentMethodOptions paymentMethodOptions;
}
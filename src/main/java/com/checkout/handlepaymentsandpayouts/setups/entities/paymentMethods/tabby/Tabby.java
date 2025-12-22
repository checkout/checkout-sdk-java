package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tabby;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodOptions;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Tabby payment method configuration
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Tabby extends PaymentMethodBase {

    /**
     * Payment method options specific to Tabby
     */
    @SerializedName("payment_method_options")
    private PaymentMethodOptions paymentMethodOptions;
}
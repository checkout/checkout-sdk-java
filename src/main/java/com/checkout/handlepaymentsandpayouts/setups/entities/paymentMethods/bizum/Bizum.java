package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bizum;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodOptions;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Bizum payment method configuration
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Bizum extends PaymentMethodBase {

    /**
     * Payment method options specific to Bizum
     */
    @SerializedName("payment_method_options")
    private PaymentMethodOptions paymentMethodOptions;
}
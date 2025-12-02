package com.checkout.handlepaymentsandpayouts.setups.entities;

import com.checkout.handlepaymentsandpayouts.setups.entities.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.PaymentMethodOptions;
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
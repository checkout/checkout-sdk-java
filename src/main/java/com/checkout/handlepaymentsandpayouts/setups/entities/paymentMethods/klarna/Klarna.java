package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodOptions;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Klarna payment method configuration
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Klarna extends PaymentMethodBase {

    /**
     * The account holder information for Klarna payments
     */
    @SerializedName("account_holder")
    private KlarnaAccountHolder accountHolder;

    /**
     * Payment method options specific to Klarna
     */
    @SerializedName("payment_method_options")
    private PaymentMethodOptions paymentMethodOptions;
}
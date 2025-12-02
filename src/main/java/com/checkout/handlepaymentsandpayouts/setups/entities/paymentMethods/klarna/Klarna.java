package com.checkout.handlepaymentsandpayouts.setups.entities;

import com.checkout.handlepaymentsandpayouts.setups.entities.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.PaymentMethodOptions;
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
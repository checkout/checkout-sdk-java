package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment method options configuration
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodOptions {

    /**
     * Klarna SDK configuration options
     */
    private PaymentMethodOption sdk;

    /**
     * STC Pay full payment option configuration
     */
    @SerializedName("pay_in_full")
    private PaymentMethodOption payInFull;

    /**
     * Tabby installments payment option configuration
     */
    private PaymentMethodOption installments;

    /**
     * Bizum immediate payment option configuration
     */
    @SerializedName("pay_now")
    private PaymentMethodOption payNow;
}
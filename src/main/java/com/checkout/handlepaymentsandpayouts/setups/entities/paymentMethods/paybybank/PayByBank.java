package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paybybank;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Pay by Bank (Open Banking) payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class PayByBank extends PaymentMethodBase {

    /**
     * The identifier of the bank the customer has selected for the payment.
     * [Optional]
     */
    private String bankId;

    /**
     * The next available action for the payment method (response only).
     * [Optional]
     */
    private PayByBankAction action;
}

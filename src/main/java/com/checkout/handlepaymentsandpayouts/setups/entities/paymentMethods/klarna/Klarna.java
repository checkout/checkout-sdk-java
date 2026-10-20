package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Klarna payment method configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Klarna extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. When you create a Payment Setup, this
     * defaults to disabled.
     * [Optional]
     * Default: disabled
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The next available action for the payment method. Contains type, client_token, and session_id.
     * [Optional] readOnly
     */
    private PaymentMethodAction action;

    /**
     * The account holder details returned by Klarna after the shopper completes verification.
     * [Optional] readOnly
     */
    private KlarnaAccountHolder accountHolder;

    /**
     * The IBAN of the account used to complete Klarna verification.
     * [Optional] readOnly
     */
    private String iban;
}

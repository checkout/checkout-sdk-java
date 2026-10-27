package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The PayPal payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class PayPal extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. When you create a Payment Setup, this
     * defaults to disabled. On update the API replaces the payment methods, so send {@code ENABLED} again to
     * keep the method enabled. The specification does not state this; it is the API's behaviour.
     * <p>[Optional]</p>
     * Default: disabled
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The user action for the PayPal widget.
     * <p>[Optional]</p>
     */
    private PayPalUserAction userAction;

    /**
     * The brand name to display in the PayPal checkout experience.
     * <p>[Optional]</p>
     * max 127 characters
     */
    private String brandName;

    /**
     * Where to obtain the shipping information.
     * <p>[Optional]</p>
     */
    private PayPalShippingPreference shippingPreference;

    /**
     * The next available action for the payment method. Contains type {@code sdk} and
     * {@code order_id}.
     * <p>[Optional] readOnly</p>
     */
    private PaymentMethodAction action;

}

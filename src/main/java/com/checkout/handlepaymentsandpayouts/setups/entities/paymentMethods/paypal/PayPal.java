package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodAction;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The PayPal payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class PayPal extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. Defaults to disabled.
     * <p>[Optional]</p>
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The user action for the PayPal widget.
     * <p>[Optional]</p>
     */
    @SerializedName("user_action")
    private PayPalUserAction userAction;

    /**
     * The brand name to display in the PayPal checkout experience.
     * <p>[Optional]</p>
     * Maximum length: 127 characters.
     */
    @SerializedName("brand_name")
    private String brandName;

    /**
     * Where to obtain the shipping information.
     * <p>[Optional]</p>
     */
    @SerializedName("shipping_preference")
    private PayPalShippingPreference shippingPreference;

    /**
     * The next available action. Contains type {@code sdk} and {@code order_id}.
     * <p>[Read-only]</p>
     */
    private PaymentMethodAction action;

}

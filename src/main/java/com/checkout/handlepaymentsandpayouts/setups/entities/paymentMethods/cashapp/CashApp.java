package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Cash App Pay payment method configuration. The customer must be sent to the redirect URL
 * returned in the action to authorize the payment with Cash App. The payment setup customer
 * device client is required for this method.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class CashApp extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. Defaults to disabled.
     * [Optional]
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * Whether the customer consents to share their Cash App customer profile with Checkout.com.
     * [Optional]
     */
    @SerializedName("customer_profile_sharing")
    private Boolean customerProfileSharing;

    /**
     * The customer's Cash App profile that they consented to share. Cash App releases it only once,
     * in the first successful response after the customer authorizes the payment.
     * [Optional] readOnly
     */
    @SerializedName("customer_profile")
    private CashAppCustomerProfile customerProfile;

    /**
     * A reference for the Cash App Pay transaction, returned by the provider. Max 80 characters.
     * [Optional] readOnly
     */
    private String reference;

    /**
     * The next available action for the payment method.
     * [Optional] readOnly
     */
    private CashAppAction action;
}

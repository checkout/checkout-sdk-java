package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
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
     * Indicates whether the customer consents to share their Cash App customer profile with
     * Checkout.com. When enabled, the customer profile is returned once, after the customer
     * authorizes the payment.
     * [Optional]
     */
    private Boolean customerProfileSharing;

    /**
     * The customer's Cash App profile that they consented to share. Included in the response when
     * customer_profile_sharing is enabled. Cash App releases this profile only once: it is present
     * in the first successful response when you get the payment setup after the customer
     * authorizes the payment, and every subsequent response omits it, so store it on first read.
     * [Optional] readOnly
     */
    private CashAppCustomerProfile customerProfile;

    /**
     * A reference for the Cash App Pay transaction, returned by the provider.
     * [Optional] readOnly
     * max 80 characters
     */
    private String reference;

    /**
     * The next available action for the payment method. When its type is redirect, send the
     * customer to its redirect URL to authorize the payment with Cash App.
     * [Optional] readOnly
     */
    private CashAppAction action;
}

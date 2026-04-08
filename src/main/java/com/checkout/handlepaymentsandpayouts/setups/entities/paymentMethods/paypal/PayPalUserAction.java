package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal;

import com.google.gson.annotations.SerializedName;

public enum PayPalUserAction {

    /**
     * After the customer clicks the PayPal button, the customer is redirected to a page to enter
     * payment details, then they are immediately directed to finalize the payment.
     */
    @SerializedName("pay_now")
    PAY_NOW,

    /**
     * After the customer clicks the PayPal button, the customer is redirected to a page to enter
     * payment details and then is redirected back to the merchant's site to review and finalize the payment.
     */
    @SerializedName("continue")
    CONTINUE

}

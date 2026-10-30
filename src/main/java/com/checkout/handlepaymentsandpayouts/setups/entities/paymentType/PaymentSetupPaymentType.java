package com.checkout.handlepaymentsandpayouts.setups.entities.paymentType;

import com.google.gson.annotations.SerializedName;

/**
 * The type of payment of a Payment Setup. The Payment Setups API uses lowercase values, unlike
 * {@link com.checkout.payments.PaymentType}, which the payments API uses. Default: regular.
 */
public enum PaymentSetupPaymentType {

    @SerializedName("regular")
    REGULAR,

    @SerializedName("recurring")
    RECURRING,

    @SerializedName("moto")
    MOTO,

    @SerializedName("installment")
    INSTALLMENT,

    @SerializedName("pay_later")
    PAY_LATER,

    @SerializedName("unscheduled")
    UNSCHEDULED
}

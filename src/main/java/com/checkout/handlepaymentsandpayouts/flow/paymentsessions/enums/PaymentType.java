package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums;

import com.google.gson.annotations.SerializedName;

/** Default: "Regular" Must be specified for card-not-present (CNP) payments. For example, a recurring mail order /
 * telephone order (MOTO) payment.
 */
public enum PaymentType {
    @SerializedName("Regular")
    REGULAR,
    @SerializedName("Recurring")
    RECURRING,
    @SerializedName("MOTO")
    MOTO,
    @SerializedName("Installment")
    INSTALLMENT,
    @SerializedName("Unscheduled")
    UNSCHEDULED
}

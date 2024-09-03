package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PaymentType {

    @SerializedName("Installment")
    INSTALLMENT,

    @SerializedName("Moto")
    MOTO,

    @SerializedName("PayLater")
    PAYLATER,

    @SerializedName("Recurring")
    RECURRING,

    @SerializedName("Regular")
    REGULAR,

    @SerializedName("Unscheduled")
    UNSCHEDULED

}
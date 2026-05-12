package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PaymentType {

    @SerializedName("Installment")
    INSTALLMENT,

    @SerializedName(value = "MOTO", alternate = "Moto")
    MOTO,

    @SerializedName("PayLater")
    PAYLATER,

    @SerializedName("Recurring")
    RECURRING,

    @SerializedName(value ="Regular", alternate = {"REGULAR", "regular"})
    REGULAR,

    @SerializedName("Unscheduled")
    UNSCHEDULED
}
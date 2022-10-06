package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PaymentType {

    @SerializedName("Regular")
    REGULAR,
    @SerializedName("Recurring")
    RECURRING,
    @SerializedName("Moto")
    MOTO,
    @SerializedName("Installment")
    INSTALLMENT
}
package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PaymentDestinationType {

    @SerializedName("card")
    CARD,
    @SerializedName("id")
    ID,
    @SerializedName("token")
    TOKEN

}

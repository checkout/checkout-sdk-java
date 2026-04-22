package com.checkout.instruments.update;

import com.google.gson.annotations.SerializedName;

public enum SepaPaymentType {

    @SerializedName("recurring")
    RECURRING,

    @SerializedName("regular")
    REGULAR

}

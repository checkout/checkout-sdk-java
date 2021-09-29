package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum Category {

    @SerializedName("payment")
    PAYMENT,
    @SerializedName("non_payment")
    NON_PAYMENT

}

package com.checkout.payments.contexts;

import com.google.gson.annotations.SerializedName;

public enum PaymentContextsItemsType {

    @SerializedName("physical")
    PHYSICAL,

    @SerializedName("digital")
    DIGITAL,
}

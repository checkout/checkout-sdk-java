package com.checkout.payments.four.sender;

import com.google.gson.annotations.SerializedName;

public enum SenderIdentificationType {

    @SerializedName("passport")
    PASSPORT,

    @SerializedName("driving_licence")
    DRIVING_LICENCE,

    @SerializedName("national_id")
    NATIONAL_ID
}

package com.checkout.payments.contexts;

import com.google.gson.annotations.SerializedName;

public enum PaymentContextDetailsStatusType {

    @SerializedName("Created")
    CREATED,

    @SerializedName("Approved")
    APPROVED,
}

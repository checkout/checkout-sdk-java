package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum ProductType {

    @SerializedName("QR Code")
    QR_CODE,
    @SerializedName("In-App")
    IN_APP,
    @SerializedName("Official Account")
    OFFICIAL_ACCOUNT,
    @SerializedName("Mini Program")
    MINI_PROGRAM

}

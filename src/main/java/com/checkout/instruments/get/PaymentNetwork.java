package com.checkout.instruments.get;

import com.google.gson.annotations.SerializedName;

public enum PaymentNetwork {

    @SerializedName("local")
    LOCAL,
    @SerializedName("sepa")
    SEPA,
    @SerializedName("fps")
    FPS,
    @SerializedName("ach")
    ACH,
    @SerializedName("fedwire")
    FEDWIRE,
    @SerializedName("swift")
    SWIFT

}

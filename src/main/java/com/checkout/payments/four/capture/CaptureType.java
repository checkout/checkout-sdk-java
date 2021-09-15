package com.checkout.payments.four.capture;

import com.google.gson.annotations.SerializedName;

public enum CaptureType {

    @SerializedName("NonFinal")
    NON_FINAL,
    @SerializedName("Final")
    FINAL,

}

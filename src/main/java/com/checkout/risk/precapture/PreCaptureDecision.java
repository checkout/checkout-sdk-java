package com.checkout.risk.precapture;

import com.google.gson.annotations.SerializedName;

public enum PreCaptureDecision {

    @SerializedName("capture")
    CAPTURE,
    @SerializedName("flag")
    FLAG,
    @SerializedName("void")
    VOID,

}

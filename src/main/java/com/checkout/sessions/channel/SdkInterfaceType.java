package com.checkout.sessions.channel;

import com.google.gson.annotations.SerializedName;

public enum SdkInterfaceType {

    @SerializedName("both")
    BOTH,
    @SerializedName("html")
    HTML,
    @SerializedName("native")
    NATIVE

}

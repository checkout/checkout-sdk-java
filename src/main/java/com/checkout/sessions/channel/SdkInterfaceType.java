package com.checkout.sessions.channel;

import com.google.gson.annotations.SerializedName;

public enum SdkInterfaceType {

    @SerializedName("native")
    NATIVE,
    @SerializedName("html")
    HTML,
    @SerializedName("both")
    BOTH

}

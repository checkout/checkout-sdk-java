package com.checkout.sessions.channel;

import com.google.gson.annotations.SerializedName;

public enum ChannelType {

    @SerializedName("app")
    APP,
    @SerializedName("browser")
    BROWSER,
    @SerializedName("merchant_initiated")
    MERCHANT_INITIATED

}

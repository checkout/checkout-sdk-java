package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationType {

    @SerializedName("regular")
    REGULAR,
    @SerializedName("recurring")
    RECURRING,

}

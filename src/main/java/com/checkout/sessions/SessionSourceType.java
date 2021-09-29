package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum SessionSourceType {

    @SerializedName("card")
    CARD,
    @SerializedName("id")
    ID,
    @SerializedName("token")
    TOKEN,

}

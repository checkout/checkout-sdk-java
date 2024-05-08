package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationType {

    @SerializedName("regular")
    REGULAR,
    @SerializedName("recurring")
    RECURRING,

    @SerializedName("installment")
    INSTALLMENT,

    @SerializedName("maintain_card")
    MAINTAIN_CARD,

    @SerializedName("add_card")
    ADD_CARD,

}

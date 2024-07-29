package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationType {

    @SerializedName("add_card")
    ADD_CARD,
    @SerializedName("installment")
    INSTALLMENT,
    @SerializedName("maintain_card")
    MAINTAIN_CARD,
    @SerializedName("recurring")
    RECURRING,
    @SerializedName("regular")
    REGULAR

}

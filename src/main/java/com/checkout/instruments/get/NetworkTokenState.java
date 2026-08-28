package com.checkout.instruments.get;

import com.google.gson.annotations.SerializedName;

/**
 * The state of a network token.
 */
public enum NetworkTokenState {

    @SerializedName("active")
    ACTIVE,

    @SerializedName("suspended")
    SUSPENDED,

    @SerializedName("inactive")
    INACTIVE,

}

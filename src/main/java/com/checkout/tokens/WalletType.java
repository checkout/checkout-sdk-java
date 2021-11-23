package com.checkout.tokens;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public enum WalletType {

    @SerializedName("applepay")
    APPLE_PAY("applepay"),
    @SerializedName("googlepay")
    GOOGLE_PAY("googlepay");

    @Getter
    private final String name;

    WalletType(final String name) {
        this.name = name;
    }

}
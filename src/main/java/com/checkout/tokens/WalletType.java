package com.checkout.tokens;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public enum WalletType {

    @SerializedName("applepay")
    APPLE_PAY("applepay"),
    @SerializedName("googlepay")
    GOOGLE_PAY("googlepay");

    private final String name;

    WalletType(final String name) {
        this.name = name;
    }

    /**
     * Will be removed in a future version.
     */
    @Deprecated
    public static WalletType from(final String name) {
        return Arrays.stream(values())
                .filter(it -> it.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    public String getName() {
        return name;
    }

}
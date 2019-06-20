package com.checkout.tokens;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public enum WalletType {
    @SerializedName("applepay")
    APPLE_PAY("applepay"),
    @SerializedName("googlepay")
    GOOGLE_PAY("googlepay");

    private String name;

    WalletType(String name) {
        this.name = name;
    }

    static public WalletType from(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name.equals(name))
                .findFirst().get();
    }

    public String getName() {
        return name;
    }
}
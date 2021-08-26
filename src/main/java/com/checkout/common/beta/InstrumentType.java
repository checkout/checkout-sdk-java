package com.checkout.common.beta;

import com.google.gson.annotations.SerializedName;

public enum InstrumentType {

    @SerializedName("bank_account")
    BANK_ACCOUNT("bank_account"),

    @SerializedName("token")
    TOKEN("token"),

    @SerializedName("card")
    CARD("card");

    private final String identifier;

    InstrumentType(final String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }

}

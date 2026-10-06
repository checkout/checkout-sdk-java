package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * The document types accepted as a sole trader's proof of registration (EEA Sole Trader Full
 * (3.0)).
 */
public enum ProofOfRegistrationType {

    @SerializedName("extract_from_trade_register")
    EXTRACT_FROM_TRADE_REGISTER,

    @SerializedName("other")
    OTHER

}

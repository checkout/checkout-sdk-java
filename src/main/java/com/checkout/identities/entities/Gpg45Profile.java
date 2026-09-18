package com.checkout.identities.entities;

import com.google.gson.annotations.SerializedName;

/**
 * The GPG 45 identity profile the verification meets.
 */
public enum Gpg45Profile {

    @SerializedName("M1A")
    M1A,
    @SerializedName("M1C")
    M1C,
    @SerializedName("H1A")
    H1A

}

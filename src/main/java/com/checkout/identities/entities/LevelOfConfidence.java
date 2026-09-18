package com.checkout.identities.entities;

import com.google.gson.annotations.SerializedName;

/**
 * The level of confidence in the verified identity.
 */
public enum LevelOfConfidence {

    @SerializedName("medium")
    MEDIUM,
    @SerializedName("high")
    HIGH

}

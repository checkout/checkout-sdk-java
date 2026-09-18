package com.checkout.identities.entities;

import com.google.gson.annotations.SerializedName;

/**
 * The type of device the applicant used to start the attempt.
 */
public enum InitialDevice {

    @SerializedName("desktop")
    DESKTOP,
    @SerializedName("mobile")
    MOBILE

}

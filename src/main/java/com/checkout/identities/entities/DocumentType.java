package com.checkout.identities.entities;

import com.google.gson.annotations.SerializedName;

/**
 * The type of identity document.
 */
public enum DocumentType {

    @SerializedName("Driving licence")
    DRIVING_LICENCE,

    @SerializedName("ID")
    ID,

    @SerializedName("Other")
    OTHER,

    @SerializedName("Passport")
    PASSPORT,

    @SerializedName("Residence Permit")
    RESIDENCE_PERMIT,

    @SerializedName("Travel Document")
    TRAVEL_DOCUMENT,

    @SerializedName("Visa")
    VISA

}

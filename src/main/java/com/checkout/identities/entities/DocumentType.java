package com.checkout.identities.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of the type of identity document.
 */

public enum DocumentType {

    @SerializedName("Driving licence")
    DRIVING_LICENCE,

    @SerializedName("ID")
    ID,

    @SerializedName("Passport")
    PASSPORT,

    @SerializedName("Residence Permit")
    RESIDENCE_PERMIT
}
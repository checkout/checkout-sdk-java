package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum DocumentType {

    @SerializedName("passport")
    PASSPORT,
    @SerializedName("national_identity_card")
    NATIONAL_IDENTITY_CARD,
    @SerializedName("driving_license")
    DRIVING_LICENSE,
    @SerializedName("citizen_card")
    CITIZEN_CARD,
    @SerializedName("residence_permit")
    RESIDENCE_PERMIT,
    @SerializedName("electoral_id")
    ELECTORAL_ID

}

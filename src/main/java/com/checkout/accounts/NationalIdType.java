package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

public enum NationalIdType {

    @SerializedName("ssn")
    SSN,
    @SerializedName("itin")
    ITIN,
    @SerializedName("passport")
    PASSPORT,
    @SerializedName("driving_license")
    DRIVING_LICENSE,
    @SerializedName("national_id_card")
    NATIONAL_ID_CARD,
    @SerializedName("residence_permit")
    RESIDENCE_PERMIT,
    @SerializedName("other")
    OTHER
}

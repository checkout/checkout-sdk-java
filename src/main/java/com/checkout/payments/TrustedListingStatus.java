package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum TrustedListingStatus {

    /** 3DS Requestor is allowlisted by cardholder. */
    @SerializedName("Y")
    ALLOWLISTED,

    /** 3DS Requestor is not allowlisted by cardholder. */
    @SerializedName("N")
    NOT_ALLOWLISTED,

    /** Not eligible as determined by issuer. */
    @SerializedName("E")
    NOT_ELIGIBLE,

    /** Pending confirmation by cardholder. */
    @SerializedName("P")
    PENDING,

    /** Cardholder rejected. */
    @SerializedName("R")
    REJECTED,

    /** Allowlist status unknown, unavailable, or does not apply. */
    @SerializedName("U")
    UNKNOWN

}

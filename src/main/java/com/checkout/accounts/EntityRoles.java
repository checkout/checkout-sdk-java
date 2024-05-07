package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

public enum EntityRoles {

    @SerializedName("ubo")
    UBO,
    @SerializedName("legal_representative")
    LEGAL_REPRESENTATIVE,
    @SerializedName("authorised_signatory")
    AUTHORISED_SIGNATORY
}

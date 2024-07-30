package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationMethod {

    @SerializedName("federated_id")
    FEDERATED_ID,
    @SerializedName("fido")
    FIDO,
    @SerializedName("issuer_credentials")
    ISSUER_CREDENTIALS,
    @SerializedName("no_authentication")
    NO_AUTHENTICATION,
    @SerializedName("own_credentials")
    OWN_CREDENTIALS,
    @SerializedName("third_party_authentication")
    THIRD_PARTY_AUTHENTICATION

}

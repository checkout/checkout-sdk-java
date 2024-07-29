package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum ThreeDsReqAuthMethodType {

    @SerializedName("federated_id")
    FEDERATED_ID,
    @SerializedName("fido_authenticator")
    FIDO_AUTHENTICATOR,
    @SerializedName("fido_authenticator_fido_assurance_data_signed")
    FIDO_AUTHENTICATOR_FIDO_ASSURANCE_DATA_SIGNED,
    @SerializedName("issuer_credentials")
    ISSUER_CREDENTIALS,
    @SerializedName("no_threeds_requestor_authentication_occurred")
    NO_THREEDS_REQUESTOR_AUTHENTICATION_OCCURRED,
    @SerializedName("src_assurance_data")
    SRC_ASSURANCE_DATA,
    @SerializedName("three3ds_requestor_own_credentials")
    THREE3DS_REQUESTOR_OWN_CREDENTIALS,
    @SerializedName("third_party_authentication")
    THIRD_PARTY_AUTHENTICATION,
    
}

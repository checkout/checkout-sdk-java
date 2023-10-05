package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum ThreeDSAuthenticationMethod {
    
    @SerializedName("frictionless_authentication")
    FRICTIONLESS_AUTHENTICATION,
    
    @SerializedName("challenge_occurred")
    CHALLENGE_OCCURRED,
    
    @SerializedName("avs_verified")
    AVS_VERIFIED,
    
    @SerializedName("other_issuer_methods")
    OTHER_ISSUER_METHODS
}

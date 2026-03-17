package com.checkout.handlepaymentsandpayouts.applepay.entities;

import com.google.gson.annotations.SerializedName;

public enum ProtocolVersions {
    
    @SerializedName("ec_v1")
    EC_V1,
    
    @SerializedName("rsa_v1")
    RSA_V1
    
}
package com.checkout.forward.requests;

import com.google.gson.annotations.SerializedName;

public enum MethodType {

    @SerializedName("GET")
    GET,
    @SerializedName("POST")
    POST,
    @SerializedName("PUT")
    PUT,
    @SerializedName("DELETE")
    DELETE,
    @SerializedName("PATCH")
    PATCH,
    @SerializedName("HEAD")
    HEAD,
    @SerializedName("OPTIONS")
    OPTIONS,
    @SerializedName("TRACE")
    TRACE

}

package com.checkout.issuing.controls.requests.controlgroup;

import com.google.gson.annotations.SerializedName;

public enum ControlGroupFailIf {
    
    @SerializedName("all_fail")
    ALL_FAIL,
    
    @SerializedName("any_fail")
    ANY_FAIL
}
package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum StatusReason {

    @SerializedName("ares_error")
    ARES_ERROR,
    @SerializedName("ares_status")
    ARES_STATUS,
    @SerializedName("veres_error")
    VERES_ERROR,
    @SerializedName("veres_status")
    VERES_STATUS,
    @SerializedName("pares_error")
    PARES_ERROR,
    @SerializedName("pares_status")
    PARES_STATUS,
    @SerializedName("rreq_error")
    RREQ_ERROR,
    @SerializedName("rreq_status")
    RREQ_STATUS,
    @SerializedName("risk_declined")
    RISK_DECLINED;

}

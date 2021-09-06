package com.checkout.risk.precapture;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class AuthorizationResult {

    @SerializedName("avs_code")
    private String avsCode;

    @SerializedName("cvv_result")
    private String cvvResult;

}

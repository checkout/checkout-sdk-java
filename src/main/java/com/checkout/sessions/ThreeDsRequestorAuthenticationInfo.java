package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public final class ThreeDsRequestorAuthenticationInfo {

    @SerializedName("three_ds_req_auth_method")
    private ThreeDsReqAuthMethodType threeDsReqAuthMethod;

    @SerializedName("three_ds_req_auth_timestamp")
    private Instant threeDsReqAuthTimestamp;

    @SerializedName("three_ds_req_auth_data")
    private String threeDsReqAuthData;

}

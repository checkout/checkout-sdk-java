package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantAuthenticationInfo {

    @SerializedName("three_ds_req_auth_method")
    private String threeDsReqAuthMethod;
    
    @SerializedName("three_ds_req_auth_timestamp")
    private Instant threeDsReqAuthTimestamp;

    @SerializedName("three_ds_req_auth_data")
    private String threeDsReqAuthData;

}

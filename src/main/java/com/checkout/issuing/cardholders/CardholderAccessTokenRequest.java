package com.checkout.issuing.cardholders;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardholderAccessTokenRequest {

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("client_id")
    private String clientID;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("cardholder_id")
    private String cardholderId;

    @SerializedName("single_use")
    private Boolean singleUse;

}

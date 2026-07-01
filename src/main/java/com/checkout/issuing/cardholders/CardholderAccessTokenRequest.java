package com.checkout.issuing.cardholders;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CardholderAccessTokenRequest {

    private String grantType;

    @SerializedName("client_id")
    private String clientID;

    private String clientSecret;

    private String cardholderId;

    private Boolean singleUse;

}

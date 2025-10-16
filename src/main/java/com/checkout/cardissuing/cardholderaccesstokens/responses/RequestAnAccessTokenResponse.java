package com.checkout.cardissuing.cardholderaccesstokens.responses;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestAnAccessTokenResponse extends HttpMetadata {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    /**
     * The remaining time the access token is valid for, in seconds
     */
    @SerializedName("expires_in")
    private Double expiresIn;

    private String scope;

}
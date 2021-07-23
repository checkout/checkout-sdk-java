package com.checkout.tokens.beta;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class TokenHeader {

    @SerializedName("ephemeralPublicKey")
    private String ephemeralPublicKey;

    @SerializedName("publicKeyHash")
    private String publicKeyHash;

    @SerializedName("transactionId")
    private String transactionId;

}

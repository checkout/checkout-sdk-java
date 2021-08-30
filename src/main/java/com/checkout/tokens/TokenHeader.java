package com.checkout.tokens;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class TokenHeader {

    @SerializedName("ephemeralPublicKey")
    private String ephemeralPublicKey;

    @SerializedName("publicKeyHash")
    private String publicKeyHash;

    @SerializedName("transactionId")
    private String transactionId;

}

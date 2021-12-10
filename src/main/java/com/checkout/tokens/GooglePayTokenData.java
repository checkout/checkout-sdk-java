package com.checkout.tokens;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class GooglePayTokenData {

    private String signature;

    @SerializedName("protocolVersion")
    private String protocolVersion;

    @SerializedName("signedMessage")
    private String signedMessage;

}

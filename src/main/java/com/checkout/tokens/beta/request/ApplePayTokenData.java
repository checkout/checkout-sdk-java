package com.checkout.tokens.beta.request;

import com.checkout.tokens.TokenHeader;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class ApplePayTokenData {

    private String version;

    private String data;

    private String signature;

    @SerializedName("header")
    private TokenHeader tokenHeader;

}

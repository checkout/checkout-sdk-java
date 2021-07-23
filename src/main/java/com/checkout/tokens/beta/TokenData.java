package com.checkout.tokens.beta;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class TokenData {

    private String version;

    private String data;

    private String signature;

    @SerializedName("header")
    private TokenHeader tokenHeader;

}

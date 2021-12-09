package com.checkout.tokens;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public final class ApplePayTokenData {

    private String version;

    private String data;

    private String signature;

    @SerializedName("header")
    private Map<String, String> tokenHeader;

}

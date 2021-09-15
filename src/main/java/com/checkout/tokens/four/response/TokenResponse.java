package com.checkout.tokens.four.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Instant;

@Data
public class TokenResponse {

    private String type;

    private String token;

    @SerializedName("expires_on")
    private Instant expiresOn;

}
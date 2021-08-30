package com.checkout.tokens;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TokenResponse extends Resource {

    private String type;

    private String token;

    @SerializedName("expires_on")
    private Instant expiresOn;

}
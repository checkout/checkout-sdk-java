package com.checkout.tokens;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class WalletTokenRequest {

    private WalletType type;

    @Builder.Default
    @SerializedName("token_data")
    private Map<String, Object> tokenData = new HashMap<>();

    public WalletTokenRequest(final WalletType type) {
        this.type = type;
    }

}

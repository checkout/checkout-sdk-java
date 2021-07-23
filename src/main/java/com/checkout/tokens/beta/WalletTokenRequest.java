package com.checkout.tokens.beta;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class WalletTokenRequest {

    private WalletType type;

    @SerializedName("token_data")
    private TokenData tokenData;

}
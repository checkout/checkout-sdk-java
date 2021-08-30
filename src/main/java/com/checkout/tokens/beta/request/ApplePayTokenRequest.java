package com.checkout.tokens.beta.request;

import com.checkout.tokens.beta.TokenType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ApplePayTokenRequest extends WalletTokenRequest {

    @SerializedName("token_data")
    private final ApplePayTokenData applePayTokenData;

    @Builder
    public ApplePayTokenRequest(final ApplePayTokenData applePayTokenData) {
        super(TokenType.APPLEPAY);
        this.applePayTokenData = applePayTokenData;
    }
}
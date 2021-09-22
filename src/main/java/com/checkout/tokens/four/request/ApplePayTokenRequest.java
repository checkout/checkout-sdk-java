package com.checkout.tokens.four.request;

import com.checkout.tokens.four.TokenType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ApplePayTokenRequest extends WalletTokenRequest {

    @SerializedName("token_data")
    private ApplePayTokenData applePayTokenData;

    @Builder
    public ApplePayTokenRequest(final ApplePayTokenData applePayTokenData) {
        super(TokenType.APPLEPAY);
        this.applePayTokenData = applePayTokenData;
    }

    public ApplePayTokenRequest() {
        super(TokenType.APPLEPAY);
    }

}
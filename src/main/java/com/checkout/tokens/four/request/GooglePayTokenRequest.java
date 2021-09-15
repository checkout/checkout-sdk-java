package com.checkout.tokens.four.request;

import com.checkout.tokens.four.TokenType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GooglePayTokenRequest extends WalletTokenRequest {

    @SerializedName("token_data")
    private final GooglePayTokenData googlePayTokenData;

    @Builder
    public GooglePayTokenRequest(final GooglePayTokenData googlePayTokenData) {
        super(TokenType.GOOGLEPAY);
        this.googlePayTokenData = googlePayTokenData;
    }

}
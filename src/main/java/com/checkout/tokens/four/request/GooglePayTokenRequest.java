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
public final class GooglePayTokenRequest extends WalletTokenRequest {

    @SerializedName("token_data")
    private GooglePayTokenData googlePayTokenData;

    @Builder
    public GooglePayTokenRequest(final GooglePayTokenData googlePayTokenData) {
        super(TokenType.GOOGLEPAY);
        this.googlePayTokenData = googlePayTokenData;
    }

    public GooglePayTokenRequest() {
        super(TokenType.GOOGLEPAY);
    }

}

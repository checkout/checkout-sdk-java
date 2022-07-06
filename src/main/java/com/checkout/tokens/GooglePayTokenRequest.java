package com.checkout.tokens;

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
    private GooglePayTokenRequest(final GooglePayTokenData googlePayTokenData) {
        super(TokenType.GOOGLEPAY);
        this.googlePayTokenData = googlePayTokenData;
    }

    public GooglePayTokenRequest() {
        super(TokenType.GOOGLEPAY);
    }

}

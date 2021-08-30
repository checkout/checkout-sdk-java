package com.checkout.tokens.beta.request;

import com.checkout.tokens.beta.TokenType;
import lombok.Data;

@Data
public abstract class WalletTokenRequest {

    private final TokenType type;

    public WalletTokenRequest(final TokenType type) {
        this.type = type;
    }

}

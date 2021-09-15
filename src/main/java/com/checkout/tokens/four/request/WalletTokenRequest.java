package com.checkout.tokens.four.request;

import com.checkout.tokens.four.TokenType;
import lombok.Data;

@Data
public abstract class WalletTokenRequest {

    private final TokenType type;

    public WalletTokenRequest(final TokenType type) {
        this.type = type;
    }

}

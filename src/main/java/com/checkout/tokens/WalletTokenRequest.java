package com.checkout.tokens;

import lombok.Data;

@Data
public abstract class WalletTokenRequest {

    private final TokenType type;

    protected WalletTokenRequest(final TokenType type) {
        this.type = type;
    }

}

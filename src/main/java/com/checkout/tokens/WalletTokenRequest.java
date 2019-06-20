package com.checkout.tokens;

import java.util.Map;

public class WalletTokenRequest implements TokenRequest {

    private WalletType type;
    private Map<String, Object> tokenData;

    public WalletTokenRequest(WalletType walletType, Map<String, Object> tokenData) {
        this.type = walletType;
        this.tokenData = tokenData;
    }

    @Override
    public String getType() {
        return type.getName();
    }

    public void setType(String type) {
        this.type = WalletType.from(type);
    }

    public WalletType getWalletType() {
        return type;
    }

    public void setWalletType(WalletType walletType) {
        this.type = walletType;
    }

    public Map<String, Object> getTokenData() {
        return tokenData;
    }

    public void setTokenData(Map<String, Object> tokenData) {
        this.tokenData = tokenData;
    }
}
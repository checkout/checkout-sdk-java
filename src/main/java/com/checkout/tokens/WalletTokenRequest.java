package com.checkout.tokens;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTokenRequest implements TokenRequest {

    private WalletType type;
    private Map<String, Object> tokenData;

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
}
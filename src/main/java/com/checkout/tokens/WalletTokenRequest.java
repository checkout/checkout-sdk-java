package com.checkout.tokens;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTokenRequest implements TokenRequest {

    private WalletType type;

    @Builder.Default
    @SerializedName("token_data")
    private Map<String, Object> tokenData = new HashMap<>();

    public WalletTokenRequest(final WalletType type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type.getName();
    }

    public WalletType getWalletType() {
        return type;
    }

    /**
     * Will be removed in a future version.
     * <p>
     * Please initialize {@link WalletType} via builder or constructor.
     */
    @Deprecated
    public void setType(final String type) {
        this.type = WalletType.from(type);
    }

    /**
     * Will be removed in a future version.
     * <p>
     * Please initialize {@link WalletType} via builder or constructor.
     */
    @Deprecated
    public void setWalletType(final WalletType walletType) {
        this.type = walletType;
    }

}
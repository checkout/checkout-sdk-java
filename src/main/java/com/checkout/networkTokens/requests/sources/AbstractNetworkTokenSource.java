package com.checkout.networkTokens.requests.sources;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractNetworkTokenSource {

    @SerializedName("type")
    private NetworkTokenSourceType type;

    public AbstractNetworkTokenSource(final NetworkTokenSourceType type) {
        this.type = type;
    }

}
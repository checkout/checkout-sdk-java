package com.checkout.networktokens.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractNetworkTokenSource {

    private NetworkTokenSourceType type;

    public AbstractNetworkTokenSource(final NetworkTokenSourceType type) {
        this.type = type;
    }

}
package com.checkout.instruments.get;

import lombok.Data;

@Data
public final class InstrumentNetworkToken {

    /**
     * The unique identifier for the network token.
     * [Optional]
     * Pattern: ^(nt)_(\w{26})$
     */
    private String id;

    /**
     * The state of the network token.
     * [Optional]
     * Enum: "active" "suspended" "inactive"
     */
    private NetworkTokenState state;

}

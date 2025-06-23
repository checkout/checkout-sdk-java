package com.checkout.forward.requests.signatures;

import lombok.Data;

@Data
public abstract class AbstractSignature {

    /**
     * The identifier of the supported signature generation method or a specific third-party service. (Required)
     */
    protected final SignatureType type;

    protected AbstractSignature(final SignatureType type) {
        this.type = type;
    }

}

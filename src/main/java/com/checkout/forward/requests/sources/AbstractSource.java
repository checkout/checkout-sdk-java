package com.checkout.forward.requests.sources;

import lombok.Data;

@Data
public abstract class AbstractSource {

    /**
     * The payment source type (Required)
     */
    protected final SourceType type;

    protected AbstractSource(final SourceType type) {
        this.type = type;
    }

}

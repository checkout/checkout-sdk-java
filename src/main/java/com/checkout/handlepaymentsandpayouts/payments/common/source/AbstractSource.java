package com.checkout.handlepaymentsandpayouts.payments.common.source;

import lombok.Data;
/**
 * Abstract source Class
 * The source of the payment
 */
@Data
public abstract class AbstractSource {

    protected final SourceType type;

    public AbstractSource(final SourceType type) {
        this.type = type;
    }

}

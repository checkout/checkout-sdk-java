package com.checkout.risk.source;

import com.checkout.common.PaymentSourceType;

public abstract class RiskPaymentRequestSource {

    protected final PaymentSourceType type;

    protected RiskPaymentRequestSource(final PaymentSourceType type) {
        this.type = type;
    }

}

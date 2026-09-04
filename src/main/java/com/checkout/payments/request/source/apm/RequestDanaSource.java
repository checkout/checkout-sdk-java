package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Dana source.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestDanaSource extends AbstractRequestSource {

    public RequestDanaSource() {
        super(PaymentSourceType.DANA);
    }
}

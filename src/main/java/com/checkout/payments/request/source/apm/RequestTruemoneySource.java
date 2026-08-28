package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * TrueMoney source.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestTruemoneySource extends AbstractRequestSource {

    public RequestTruemoneySource() {
        super(PaymentSourceType.TRUEMONEY);
    }
}

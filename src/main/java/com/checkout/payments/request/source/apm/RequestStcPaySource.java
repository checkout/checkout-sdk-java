package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * STC Pay request source.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestStcPaySource extends AbstractRequestSource {

    public RequestStcPaySource() {
        super(PaymentSourceType.STCPAY);
    }
}

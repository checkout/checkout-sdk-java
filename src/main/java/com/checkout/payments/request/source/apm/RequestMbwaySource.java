package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * MBWay payment request source.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestMbwaySource extends AbstractRequestSource {

    public RequestMbwaySource() {
        super(PaymentSourceType.MBWAY);
    }
}

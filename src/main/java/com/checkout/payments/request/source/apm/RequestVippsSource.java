package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Vipps request source.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestVippsSource extends AbstractRequestSource {

    public RequestVippsSource() {
        super(PaymentSourceType.VIPPS);
    }
}

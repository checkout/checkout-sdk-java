package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class RequestAlipayPlusGCashSource extends AbstractRequestSource {

    public RequestAlipayPlusGCashSource() {
        super(PaymentSourceType.GCASH);
    }

}

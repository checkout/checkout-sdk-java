package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestKakaopaySource extends AbstractRequestSource {

    public RequestKakaopaySource() {
        super(PaymentSourceType.KAKAOPAY);
    }
}

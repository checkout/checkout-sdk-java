package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class RequestAlipayPlusCNSource extends AbstractRequestSource {

    public RequestAlipayPlusCNSource() {
        super(PaymentSourceType.ALIPAY_CN);
    }

}
package com.checkout.payments.four.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.four.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class RequestAlipayPlusHKSource extends AbstractRequestSource {

    public RequestAlipayPlusHKSource() {
        super(PaymentSourceType.ALIPAY_HK);
    }

}

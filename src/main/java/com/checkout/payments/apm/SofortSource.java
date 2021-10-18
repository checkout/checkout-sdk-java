package com.checkout.payments.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.RequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SofortSource extends com.checkout.payments.four.request.source.RequestSource implements RequestSource {

    public SofortSource() {
        super(PaymentSourceType.SOFORT);
    }

}

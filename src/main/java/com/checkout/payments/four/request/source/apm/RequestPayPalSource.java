package com.checkout.payments.four.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.four.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RequestPayPalSource extends AbstractRequestSource {

    public RequestPayPalSource() {
        super(PaymentSourceType.PAYPAL);
    }
}

package com.checkout.payments.response.source.contexts;

import com.checkout.common.PaymentSourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentContextsPayPalResponseSource extends AbstractPaymentContextsResponseSource implements ResponseSource {

    public PaymentContextsPayPalResponseSource() {
        super(PaymentSourceType.PAYPAL);
    }

}

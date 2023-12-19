package com.checkout.payments.request.source.contexts;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentContextsPayPalSource extends AbstractRequestSource {

    @Builder
    private PaymentContextsPayPalSource() {
        super(PaymentSourceType.PAYPAL);
    }
}

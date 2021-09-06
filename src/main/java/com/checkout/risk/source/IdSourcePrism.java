package com.checkout.risk.source;

import com.checkout.common.PaymentSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IdSourcePrism extends RiskPaymentRequestSource {

    private final String id;

    private final String cvv;

    @Builder
    protected IdSourcePrism(final String id, final String cvv) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.cvv = cvv;
    }

}

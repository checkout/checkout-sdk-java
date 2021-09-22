package com.checkout.risk.source;

import com.checkout.common.PaymentSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IdSourcePrism extends RiskPaymentRequestSource {

    private String id;

    private String cvv;

    @Builder
    private IdSourcePrism(final String id, final String cvv) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.cvv = cvv;
    }

    public IdSourcePrism() {
        super(PaymentSourceType.ID);
    }

}

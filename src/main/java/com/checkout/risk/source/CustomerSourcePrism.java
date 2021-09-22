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
public final class CustomerSourcePrism extends RiskPaymentRequestSource {

    private String id;

    @Builder
    protected CustomerSourcePrism(final String id) {
        super(PaymentSourceType.CUSTOMER);
        this.id = id;
    }

    public CustomerSourcePrism() {
        super(PaymentSourceType.CUSTOMER);
    }

}

package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.BillingPlan;
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
public final class RequestPayPalSource extends AbstractRequestSource {

    private BillingPlan plan;

    @Builder
    private RequestPayPalSource(final BillingPlan plan) {
        super(PaymentSourceType.PAYPAL);
        this.plan = plan;
    }

    public RequestPayPalSource() {
        super(PaymentSourceType.PAYPAL);
    }
}

package com.checkout.payments.request.source.apm;

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
public final class RequestEpsSource extends AbstractRequestSource {

    private String purpose;

    @Builder
    private RequestEpsSource(final String purpose) {
        super(PaymentSourceType.EPS);
        this.purpose = purpose;
    }

    public RequestEpsSource() {
        super(PaymentSourceType.EPS);
    }

}

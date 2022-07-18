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

    private String bic;

    @Builder
    private RequestEpsSource(final String purpose,
                             final String bic) {
        super(PaymentSourceType.EPS);
        this.purpose = purpose;
        this.bic = bic;
    }

    public RequestEpsSource() {
        super(PaymentSourceType.EPS);
    }

}

package com.checkout.payments.previous.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestSepaSource extends AbstractRequestSource {

    private String id;

    @Builder
    private RequestSepaSource(final String id) {
        super(PaymentSourceType.SEPA);
        this.id = id;
    }

    public RequestSepaSource() {
        super(PaymentSourceType.SEPA);
    }

}

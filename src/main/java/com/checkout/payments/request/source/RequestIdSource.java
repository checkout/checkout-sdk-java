package com.checkout.payments.request.source;

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
public final class RequestIdSource extends AbstractRequestSource {

    private String id;

    private String cvv;

    @Builder
    private RequestIdSource(final String id,
                            final String cvv) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.cvv = cvv;
    }

    public RequestIdSource() {
        super(PaymentSourceType.ID);
    }

}

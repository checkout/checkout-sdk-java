package com.checkout.payments.previous.request.source;

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

    private Boolean stored;

    private Boolean storeForFutureUse;

    @Builder
    private RequestIdSource(final String id,
                            final String cvv,
                            final Boolean stored,
                            final Boolean storeForFutureUse) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.cvv = cvv;
        this.stored = stored;
        this.storeForFutureUse = storeForFutureUse;
    }

    public RequestIdSource() {
        super(PaymentSourceType.ID);
    }

}

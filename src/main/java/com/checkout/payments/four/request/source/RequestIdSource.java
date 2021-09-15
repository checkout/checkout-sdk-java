package com.checkout.payments.four.request.source;

import com.checkout.common.PaymentSourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestIdSource extends RequestSource {

    private final String id;

    private final Integer ccv;

    @Builder
    private RequestIdSource(final String id, final Integer ccv) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.ccv = ccv;
    }

}

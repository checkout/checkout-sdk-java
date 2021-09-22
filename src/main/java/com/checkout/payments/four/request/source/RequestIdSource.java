package com.checkout.payments.four.request.source;

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
public final class RequestIdSource extends RequestSource {

    private String id;

    private Integer ccv;

    @Builder
    private RequestIdSource(final String id, final Integer ccv) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.ccv = ccv;
    }

    public RequestIdSource() {
        super(PaymentSourceType.ID);
    }

}

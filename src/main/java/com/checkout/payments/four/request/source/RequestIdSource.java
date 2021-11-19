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

    private Integer cvv;

    @Builder
    private RequestIdSource(final String id, final Integer cvv) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.cvv = cvv;
    }

    public RequestIdSource() {
        super(PaymentSourceType.ID);
    }

}

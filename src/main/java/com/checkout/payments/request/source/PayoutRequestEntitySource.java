package com.checkout.payments.request.source;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PayoutRequestEntitySource extends PayoutRequestSource {

    private String id;

    @Builder
    private PayoutRequestEntitySource(final Long amount,
                                      final String id) {
        super(PayoutSourceType.ENTITY, amount);
        this.id = id;
    }

    public PayoutRequestEntitySource() {
        super(PayoutSourceType.ENTITY);
    }

}
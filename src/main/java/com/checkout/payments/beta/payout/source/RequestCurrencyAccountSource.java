package com.checkout.payments.beta.payout.source;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestCurrencyAccountSource extends PayoutRequestSource {

    private final String id;

    @Builder
    private RequestCurrencyAccountSource(final String id, final Long amount) {
        super(PayoutSourceType.CURRENCY_ACCOUNT, amount);
        this.id = id;
    }

}

package com.checkout.payments.four.payout.source;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestCurrencyAccountSource extends PayoutRequestSource {

    private String id;

    @Builder
    private RequestCurrencyAccountSource(final String id, final Long amount) {
        super(PayoutSourceType.CURRENCY_ACCOUNT);
        this.id = id;
        this.amount = amount;
    }

    public RequestCurrencyAccountSource() {
        super(PayoutSourceType.CURRENCY_ACCOUNT);
    }

}

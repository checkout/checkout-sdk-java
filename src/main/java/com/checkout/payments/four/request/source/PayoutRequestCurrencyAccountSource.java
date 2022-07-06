package com.checkout.payments.four.request.source;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PayoutRequestCurrencyAccountSource extends PayoutRequestSource {

    private String id;

    @Builder
    private PayoutRequestCurrencyAccountSource(final Long amount,
                                              final String id) {
        super(PayoutSourceType.CURRENCY_ACCOUNT, amount);
        this.id = id;
    }

    public PayoutRequestCurrencyAccountSource() {
        super(PayoutSourceType.CURRENCY_ACCOUNT);
    }

}

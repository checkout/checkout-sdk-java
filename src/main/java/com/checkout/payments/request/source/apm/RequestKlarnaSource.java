package com.checkout.payments.request.source.apm;

import com.checkout.common.AccountHolder;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestKlarnaSource extends AbstractRequestSource {

    /**
     * The account holder's details.
     * [Optional]
     */
    private AccountHolder accountHolder;

    @Builder
    private RequestKlarnaSource(final AccountHolder accountHolder) {
        super(PaymentSourceType.KLARNA);
        this.accountHolder = accountHolder;
    }

    public RequestKlarnaSource() {
        super(PaymentSourceType.KLARNA);
    }
}

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
public final class RequestEpsSource extends AbstractRequestSource {

    /**
     * The purpose of the payment.
     * [Optional]
     */
    private String purpose;

    /**
     * The account holder's details.
     * [Optional]
     */
    private AccountHolder accountHolder;

    @Builder
    private RequestEpsSource(final String purpose,
                             final AccountHolder accountHolder) {
        super(PaymentSourceType.EPS);
        this.purpose = purpose;
        this.accountHolder = accountHolder;
    }

    public RequestEpsSource() {
        super(PaymentSourceType.EPS);
    }

}

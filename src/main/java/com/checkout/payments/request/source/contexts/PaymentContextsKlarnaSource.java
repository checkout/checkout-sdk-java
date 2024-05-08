package com.checkout.payments.request.source.contexts;

import com.checkout.common.AccountHolder;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentContextsKlarnaSource extends AbstractRequestSource {

    @SerializedName("account_holder")
    private AccountHolder accountHolder;


    @Builder
    private PaymentContextsKlarnaSource(final AccountHolder accountHolder) {
        super(PaymentSourceType.KLARNA);
        this.accountHolder = accountHolder;

    }

    public PaymentContextsKlarnaSource() {
        super(PaymentSourceType.KLARNA);
    }
}

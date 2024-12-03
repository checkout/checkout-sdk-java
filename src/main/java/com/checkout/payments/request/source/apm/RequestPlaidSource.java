package com.checkout.payments.request.source.apm;

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
public final class RequestPlaidSource extends AbstractRequestSource {

    @SerializedName("token")
    private String token;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private RequestPlaidSource(final String token, final AccountHolder accountHolder) {
        super(PaymentSourceType.PLAID);
        this.token = token;
        this.accountHolder = accountHolder;
    }

    public RequestPlaidSource() {
        super(PaymentSourceType.PLAID);
    }
}
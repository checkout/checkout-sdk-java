package com.checkout.payments.four.request.source;

import com.checkout.common.PaymentSourceType;
import com.checkout.common.four.AccountHolder;
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
public final class RequestProviderTokenSource extends AbstractRequestSource {

    @SerializedName("payment_method")
    private String payment_method;

    private String token;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private RequestProviderTokenSource(final String payment_method,
                                       final String token,
                                       final AccountHolder accountHolder) {
        super(PaymentSourceType.PROVIDER_TOKEN);
        this.payment_method = payment_method;
        this.token = token;
        this.accountHolder = accountHolder;
    }

    public RequestProviderTokenSource() {
        super(PaymentSourceType.PROVIDER_TOKEN);
    }

}

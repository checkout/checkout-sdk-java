package com.checkout.payments.request.source;

import com.checkout.common.AccountHolder;
import com.checkout.common.PaymentSourceType;
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
public final class RequestIdSource extends AbstractRequestSource {

    private String id;

    private String cvv;

    @SerializedName("payment_method")
    private String paymentMethod;

    private Boolean stored;

    @SerializedName("store_for_future_use")
    private Boolean storeForFutureUse;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private RequestIdSource(final String id,
                            final String cvv,
                            final String paymentMethod,
                            final Boolean stored,
                            final Boolean storeForFutureUse,
                            final AccountHolder accountHolder) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.cvv = cvv;
        this.paymentMethod = paymentMethod;
        this.stored = stored;
        this.storeForFutureUse = storeForFutureUse;
        this.accountHolder = accountHolder;
    }

    public RequestIdSource() {
        super(PaymentSourceType.ID);
    }

}

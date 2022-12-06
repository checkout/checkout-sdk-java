package com.checkout.payments.request.source;

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

    @Builder
    private RequestIdSource(final String id,
                            final String cvv,
                            final String paymentMethod,
                            final Boolean stored,
                            final Boolean storeForFutureUse) {
        super(PaymentSourceType.ID);
        this.id = id;
        this.cvv = cvv;
        this.paymentMethod = paymentMethod;
        this.stored = stored;
        this.storeForFutureUse = storeForFutureUse;
    }

    public RequestIdSource() {
        super(PaymentSourceType.ID);
    }

}

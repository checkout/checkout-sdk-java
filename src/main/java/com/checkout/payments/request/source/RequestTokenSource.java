package com.checkout.payments.request.source;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
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
public final class RequestTokenSource extends AbstractRequestSource {

    private String token;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    @SerializedName("store_for_future_use")
    private Boolean storeForFutureUse;

    @Builder
    private RequestTokenSource(final String token,
                               final Address billingAddress,
                               final Phone phone,
                               final Boolean storeForFutureUse) {
        super(PaymentSourceType.TOKEN);
        this.token = token;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.storeForFutureUse = storeForFutureUse;
    }

    public RequestTokenSource() {
        super(PaymentSourceType.TOKEN);
    }

}

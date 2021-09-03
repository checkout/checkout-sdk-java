package com.checkout.payments.beta.request.source;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestTokenSource extends RequestSource {

    private final String token;

    @SerializedName("billing_address")
    private final Address billingAddress;

    private final Phone phone;

    @Builder
    private RequestTokenSource(final String token,
                               final Address billingAddress,
                               final Phone phone) {
        super(PaymentSourceType.TOKEN);
        this.token = token;
        this.billingAddress = billingAddress;
        this.phone = phone;
    }

}

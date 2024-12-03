package com.checkout.payments.request.source.apm;

import com.checkout.common.Address;
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
public final class RequestSequraSource extends AbstractRequestSource {

    @SerializedName("billing_address")
    private Address billingAddress;

    @Builder
    private RequestSequraSource(final Address billingAddress) {
        super(PaymentSourceType.SEQURA);
        this.billingAddress = billingAddress;
    }

    public RequestSequraSource() {
        super(PaymentSourceType.SEQURA);
    }
}
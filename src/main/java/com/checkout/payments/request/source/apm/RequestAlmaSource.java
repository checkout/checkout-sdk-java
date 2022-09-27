package com.checkout.payments.request.source.apm;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestAlmaSource extends AbstractRequestSource {

    @SerializedName("billing_address")
    private Address billingAddress;

    @Builder
    private RequestAlmaSource(final Address billingAddress) {
        super(PaymentSourceType.ALMA);
        this.billingAddress = billingAddress;
    }

    public RequestAlmaSource() {
        super(PaymentSourceType.ALMA);
    }
}

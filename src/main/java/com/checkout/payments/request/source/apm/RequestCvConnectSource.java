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
public final class RequestCvConnectSource extends AbstractRequestSource {

    @SerializedName("billing_address")
    private Address billingAddress;

    @Builder
    private RequestCvConnectSource(final Address billingAddress) {
        super(PaymentSourceType.CV_CONNECT);
        this.billingAddress = billingAddress;
    }

    public RequestCvConnectSource() {
        super(PaymentSourceType.CV_CONNECT);
    }
}

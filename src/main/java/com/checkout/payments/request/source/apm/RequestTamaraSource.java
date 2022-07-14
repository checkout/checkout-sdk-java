package com.checkout.payments.request.source.apm;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestTamaraSource extends AbstractRequestSource {

    public RequestTamaraSource() {
        super(PaymentSourceType.TAMARA);
    }

    @SerializedName("billing_address")
    private Address billingAddress;

}

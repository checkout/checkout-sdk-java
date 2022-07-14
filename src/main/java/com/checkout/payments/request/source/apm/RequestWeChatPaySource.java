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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class RequestWeChatPaySource extends AbstractRequestSource {

    @SerializedName("billing_address")
    private Address billingAddress;

    public RequestWeChatPaySource() {
        super(PaymentSourceType.WECHATPAY);
    }

    @Builder
    private RequestWeChatPaySource(final Address billingAddress) {
        super(PaymentSourceType.WECHATPAY);
        this.billingAddress = billingAddress;
    }
}

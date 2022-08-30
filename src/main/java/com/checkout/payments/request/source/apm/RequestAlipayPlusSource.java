package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class RequestAlipayPlusSource extends AbstractRequestSource {

    private RequestAlipayPlusSource(final PaymentSourceType type) {
        super(type);
    }

    public static RequestAlipayPlusSource requestAlipayPlusSource() {
        return new RequestAlipayPlusSource(PaymentSourceType.ALIPAY_PLUS);
    }

    public static RequestAlipayPlusSource requestAlipayPlusCNSource() {
        return new RequestAlipayPlusSource(PaymentSourceType.ALIPAY_CN);
    }

    public static RequestAlipayPlusSource requestAlipayPlusGCashSource() {
        return new RequestAlipayPlusSource(PaymentSourceType.GCASH);
    }

    public static RequestAlipayPlusSource requestAlipayPlusHKSource() {
        return new RequestAlipayPlusSource(PaymentSourceType.ALIPAY_HK);
    }

    public static RequestAlipayPlusSource requestAlipayPlusDanaSource() {
        return new RequestAlipayPlusSource(PaymentSourceType.DANA);
    }

    public static RequestAlipayPlusSource requestAlipayPlusKakaoPaySource() {
        return new RequestAlipayPlusSource(PaymentSourceType.KAKAOPAY);
    }

    public static RequestAlipayPlusSource requestAlipayPlusTrueMoneySource() {
        return new RequestAlipayPlusSource(PaymentSourceType.TRUEMONEY);
    }

    public static RequestAlipayPlusSource requestAlipayPlusTNGSource() {
        return new RequestAlipayPlusSource(PaymentSourceType.TNG);
    }
}

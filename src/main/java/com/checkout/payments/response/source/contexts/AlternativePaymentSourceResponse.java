package com.checkout.payments.response.source.contexts;

import com.checkout.common.CheckoutUtils;
import com.checkout.common.PaymentSourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.EnumUtils;

import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AlternativePaymentSourceResponse extends HashMap<String, Object> implements ResponseSource {

    @Override
    public PaymentSourceType getType() {
        return EnumUtils.getEnumIgnoreCase(PaymentSourceType.class, (String) get(CheckoutUtils.TYPE));
    }

}

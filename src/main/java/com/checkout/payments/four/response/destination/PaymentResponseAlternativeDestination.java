package com.checkout.payments.four.response.destination;

import com.checkout.common.CheckoutUtils;
import com.checkout.payments.PaymentDestinationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.EnumUtils;

import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentResponseAlternativeDestination extends HashMap<String, Object> implements PaymentResponseDestination {

    @Override
    public PaymentDestinationType getType() {
        return EnumUtils.getEnumIgnoreCase(PaymentDestinationType.class, (String) get(CheckoutUtils.TYPE));
    }

}

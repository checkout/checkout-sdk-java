package com.checkout.payments;

import com.checkout.common.CheckoutUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;

/**
 * @deprecated Use a specific payment source at {@link com.checkout.payments.apm}
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Deprecated
public class AlternativePaymentSource extends HashMap<String, Object> implements RequestSource {
    private static final String TYPE_FIELD = "type";

    public AlternativePaymentSource(final String type) {
        if (CheckoutUtils.isNullOrWhitespace(type)) {
            throw new IllegalArgumentException("The alternative payment source type is required.");
        }
        setType(type);
    }

    @Override
    public String getType() {
        return get(TYPE_FIELD).toString();
    }

    public void setType(final String type) {
        put(TYPE_FIELD, type);
    }
}
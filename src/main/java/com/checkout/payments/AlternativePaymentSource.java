package com.checkout.payments;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;

import static com.checkout.common.CheckoutUtils.validateParams;

/**
 * @deprecated Use a specific payment source at {@link com.checkout.payments.apm}
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Deprecated
public class AlternativePaymentSource extends HashMap<String, Object> implements RequestSource {
    private static final String TYPE_FIELD = "type";

    public AlternativePaymentSource(final String type) {
        validateParams("type", type);
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
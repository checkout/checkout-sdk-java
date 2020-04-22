package com.checkout.payments;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AlternativePaymentSourceResponse extends HashMap<String, Object> implements ResponseSource {
    private static final String TYPE_FIELD = "type";

    @Override
    public String getType() {
        return get(TYPE_FIELD).toString();
    }

    public void setType(String type) {
        put(TYPE_FIELD, type);
    }
}
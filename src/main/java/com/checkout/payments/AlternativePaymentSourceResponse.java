package com.checkout.payments;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AlternativePaymentSourceResponse extends HashMap<String, Object> implements ResponseSource {
    private String type;

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
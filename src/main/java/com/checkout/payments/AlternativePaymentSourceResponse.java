package com.checkout.payments;

import java.util.HashMap;

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
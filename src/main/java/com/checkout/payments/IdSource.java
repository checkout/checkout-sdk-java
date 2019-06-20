package com.checkout.payments;

import com.checkout.common.CheckoutUtils;

public class IdSource implements RequestSource {
    public static final String TYPE_NAME = "id";
    private String id;
    private String cvv;

    private final String type = TYPE_NAME;

    public IdSource(String id) {
        if (CheckoutUtils.isNullOrWhitespace(id)) {
            throw new IllegalArgumentException("The source ID is required");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String getType() {
        return TYPE_NAME;
    }
}
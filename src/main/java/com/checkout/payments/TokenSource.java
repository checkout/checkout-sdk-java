package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.CheckoutUtils;

public class TokenSource implements RequestSource {
    public static final String TYPE_NAME = "token";
    private final String token;
    private Address billingAddress;

    private final String type = TYPE_NAME;

    public TokenSource(String token) {
        if (CheckoutUtils.isNullOrWhitespace(token)) {
            throw new IllegalArgumentException("The token must be provided.");
        }

        this.token = token;
    }

    @Override
    public String getType() {
        return TYPE_NAME;
    }

    public String getToken() {
        return token;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}
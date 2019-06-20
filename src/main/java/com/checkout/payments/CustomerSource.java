package com.checkout.payments;

import com.checkout.common.CheckoutUtils;

import java.util.Arrays;

public class CustomerSource implements RequestSource {
    public static final String TYPE_NAME = "customer";
    private final String id;
    private final String email;

    private final String type = TYPE_NAME;

    public CustomerSource(String id, String email) {
        if (CheckoutUtils.isNullOrWhitespace(id) && CheckoutUtils.isNullOrWhitespace(email))
            throw new IllegalArgumentException("Either the customer id or email is required.");

        if (!CheckoutUtils.isNullOrWhitespace(email) && !IsValidEmail(email))
            throw new IllegalArgumentException("The provided customer email " + email + " is invalid.");

        this.email = email;
        this.id = id;
    }

    private static boolean IsValidEmail(String email) {
        String[] parts = email.split("@");
        return parts.length == 2 && Arrays.stream(parts).noneMatch(CheckoutUtils::isNullOrWhitespace);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getType() {
        return TYPE_NAME;
    }
}
package com.checkout.payments;

public class BillingDescriptor {
    private final String name;
    private final String city;

    public BillingDescriptor(String name,
                             String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
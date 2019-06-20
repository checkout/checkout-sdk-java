package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Phone;

public class CardSource implements RequestSource {
    public static final String TYPE_NAME = "card";
    private final String number;
    private final int expiryMonth;
    private final int expiryYear;
    private String name;
    private String cvv;
    private Address billingAddress;
    private Phone phone;

    private final String type = TYPE_NAME;

    public CardSource(String number, int expiryMonth, int expiryYear) {
        if (CheckoutUtils.isNullOrWhitespace(number)) {
            throw new IllegalArgumentException("The card number is required.");
        }
        if (expiryMonth < 1 || expiryMonth > 12) {
            throw new IllegalArgumentException("The expiry month must be between 1 and 12");
        }

        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    @Override
    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
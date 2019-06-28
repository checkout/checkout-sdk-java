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
    private String firstName;
    private String lastName;
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

    /**
     * Set the full name of a payin card source object.
     * When using card as a payout destination you should use
     * {@link #setFirstName(String)} and {@link #setLastName(String)}
     * @param name the full name of the source card holder
     */
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

    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of a payout card destination object.
     * When using card as a payin source you should use {@link #setName(String)}
     * @param firstName the first name of the destination card holder
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of a payout card destination object.
     * When using card as a payin source you should use {@link #setName(String)}
     * @param lastName the last name of the destination card holder
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
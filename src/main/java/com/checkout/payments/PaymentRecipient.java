package com.checkout.payments;

import com.checkout.common.CheckoutUtils;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class PaymentRecipient {
    @SerializedName("dob")
    private final LocalDate dateOfBirth;
    private final String accountNumber;
    private final String zip;
    private final String firstName;
    private final String lastName;

    public PaymentRecipient(LocalDate dateOfBirth,
                            String accountNumber,
                            String zip,
                            String firstName,
                            String lastName) {
        if (CheckoutUtils.isNullOrWhitespace(accountNumber) || accountNumber.length() < 10) {
            throw new IllegalArgumentException("The recipient's account number must be provided. See https://docs.checkout.com/docs/requirements-for-financial-institutions for scheme specific rules.");
        }
        if (CheckoutUtils.isNullOrWhitespace(zip)) {
            throw new IllegalArgumentException("The recipient's zip must be provided.");
        }
        if (CheckoutUtils.isNullOrWhitespace(lastName)) {
            throw new IllegalArgumentException("The recipient's last name must be provided.");
        }

        this.dateOfBirth = dateOfBirth;
        this.accountNumber = accountNumber;
        this.zip = zip;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getZip() {
        return zip;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
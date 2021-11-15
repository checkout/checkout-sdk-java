package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentRecipient {

    @SerializedName("dob")
    private final LocalDate dateOfBirth;
    private final String accountNumber;
    private final String zip;
    private final String firstName;
    private final String lastName;
    private final CountryCode country;

    public PaymentRecipient(final LocalDate dateOfBirth,
                            final String accountNumber,
                            final String zip,
                            final String firstName,
                            final String lastName,
                            final CountryCode country) {
        this.dateOfBirth = dateOfBirth;
        this.accountNumber = accountNumber;
        this.zip = zip;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }

}

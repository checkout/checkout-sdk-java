package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class PaymentRecipient {

    @SerializedName("dob")
    private final LocalDate dateOfBirth;
    private final String accountNumber;
    private final String zip;
    private final String firstName;
    private final String lastName;
    private final CountryCode country;

    public PaymentRecipient(LocalDate dateOfBirth,
                            String accountNumber,
                            String zip,
                            String firstName,
                            String lastName,
                            String country) {
        this.dateOfBirth = dateOfBirth;
        this.accountNumber = accountNumber;
        this.zip = zip;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = CountryCode.fromString(country);
    }

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public String getCountry() {
        return Optional.ofNullable(country).map(CountryCode::name).orElse(null);
    }
}
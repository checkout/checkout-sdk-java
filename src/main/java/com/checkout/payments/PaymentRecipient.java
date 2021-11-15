package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PaymentRecipient {

    @SerializedName("dob")
    private String dateOfBirth;

    @SerializedName("account_number")
    private String accountNumber;

    private String zip;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private CountryCode country;

}
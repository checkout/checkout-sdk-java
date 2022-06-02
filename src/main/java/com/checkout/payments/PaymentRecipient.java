package com.checkout.payments;

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

    @SerializedName("last_name")
    private String lastName;

}
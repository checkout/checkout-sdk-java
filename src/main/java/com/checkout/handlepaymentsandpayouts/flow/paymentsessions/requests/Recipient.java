package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Recipient {

    /**
     * The recipient's date of birth, in the format YYYY-MM-DD.
     */
    private Instant dob;

    /**
     * An identifier related to the primary recipient's account. For example, an IBAN, an internal account number, a
     * phone number, or the first six and last four digits of the PAN.
     */
    @SerializedName("account_number")
    private String accountNumber;

    /**
     * The recipient's address.
     */
    private Address address;

    /**
     * The recipient's first name.
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The recipient's last name.
     */
    @SerializedName("last_name")
    private String lastName;

}

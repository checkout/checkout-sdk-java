package com.checkout.payments;

import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PaymentRecipient {

    /**
     * The recipient's date of birth.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("dob")
    private String dateOfBirth;

    /**
     * Any identifier related to the primary recipient's account, such as part of the PAN,
     * an IBAN, an internal account number, or a phone number.
     * [Optional]
     */
    private String accountNumber;

    /**
     * The recipient's address.
     * [Optional]
     */
    private Address address;

    /**
     * The first part of the UK postcode (e.g., SW1A 1AA would be SW1A). Replaced by address.zip.
     * [Optional]
     * @deprecated Use {@code address.zip} instead.
     */
    @Deprecated
    private String zip;

    /**
     * The recipient's first name.
     * [Optional]
     */
    private String firstName;

    /**
     * The recipient's last name.
     * [Optional]
     */
    private String lastName;
}

package com.checkout.payments.contexts;

import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsPassenger {

    /**
     * The passenger's first name.
     * [Optional]
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The passenger's last name.
     * [Optional]
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The passenger's date of birth.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * The passenger's address.
     * [Optional]
     */
    private Address address;
}

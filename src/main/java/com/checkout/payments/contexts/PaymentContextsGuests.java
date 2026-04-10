package com.checkout.payments.contexts;

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
public final class PaymentContextsGuests {

    /**
     * The first name of the guest.
     * [Optional]
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The last name of the guest.
     * [Optional]
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The date of birth of the guest.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("date_of_birth")
    private LocalDate dateOfBirth;
}

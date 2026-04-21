package com.checkout.payments;

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
public final class AccommodationGuest {

    /**
     * The guest's first name.
     * [Optional]
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The guest's last name.
     * [Optional]
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The guest's date of birth.
     * [Optional]
     * Format: date (yyyy-MM-dd)
     */
    @SerializedName("date_of_birth")
    private LocalDate dateOfBirth;

}

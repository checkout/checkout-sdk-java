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
public final class Guest {

    /**
     * The first name of the guest.
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The last name of the guest.
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The date of birth of the guest.
     */
    @SerializedName("date_of_birth")
    private Instant dateOfBirth;

}

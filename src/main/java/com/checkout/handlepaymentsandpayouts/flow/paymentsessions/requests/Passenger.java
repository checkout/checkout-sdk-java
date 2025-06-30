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
public final class Passenger {

    /**
     * The passenger's first name.
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The passenger's last name.
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The passenger's date of birth.
     */
    @SerializedName("date_of_birth")
    private Instant dateOfBirth;

    /**
     * Contains information about the passenger's address.
     */
    private Address address;

}

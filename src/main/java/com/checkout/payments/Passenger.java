package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Passenger {

    private PassengerName name;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("country_code")
    private CountryCode countryCode;
}

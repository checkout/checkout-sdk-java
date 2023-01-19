package com.checkout.payments.previous.request;

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
public final class SenderInformation {

    private String reference;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("dob")
    private String dob;

    private String address;

    private String city;

    private String state;

    private CountryCode country;

    @SerializedName("postalCode")
    private String postalCode;

    @SerializedName("sourceOfFunds")
    private String sourceOfFunds;

    private String purpose;

}

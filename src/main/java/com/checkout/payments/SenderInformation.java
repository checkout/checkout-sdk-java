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
public class SenderInformation {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    private String address;

    private String city;

    @SerializedName("postalCode")
    private String postalCode;

    private String state;

    private CountryCode country;

    @SerializedName("sourceOfFunds")
    private String sourceOfFunds;

    @SerializedName("accountNumber")
    private String accountNumber;

    private String reference;

}


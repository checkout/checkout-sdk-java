package com.checkout.common.beta;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Address {

    @SerializedName("address_line_1")
    private String addressLine1;

    @SerializedName("address_line_2")
    private String addressLine2;

    private String city;

    private String state;

    private String zip;

    private String country;

}
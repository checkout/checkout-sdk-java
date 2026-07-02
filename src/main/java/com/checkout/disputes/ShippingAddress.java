package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ShippingAddress {

    private String address;

    @SerializedName("address_2")
    private String address2;

    private String city;

    private String stateRegion;

    private String postalCode;

    private String country;

}

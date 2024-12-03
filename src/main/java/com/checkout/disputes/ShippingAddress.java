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

    @SerializedName("state_region")
    private String stateRegion;

    @SerializedName("postal_code")
    private String postalCode;

    private String country;

}

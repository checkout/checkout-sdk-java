package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.sessions.DeliveryTimeframe;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ShippingDetails {

    @SerializedName("first_name")
    private String firstName;
    
    @SerializedName("last_name")
    private String lastName;
    
    @SerializedName("email")
    private String email;

    private Address address;

    private Phone phone;

    @SerializedName("from_address_zip")
    private String fromAddressZip;
    
    @SerializedName("timeframe")
    private ShippingDetailsTimeframe timeframe;

    @SerializedName("method")
    private ShippingDetailsMethods method;

    private Integer delay;

}

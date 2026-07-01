package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.sessions.DeliveryTimeframe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ShippingDetails {

    private String firstName;
    
    private String lastName;
    
    private String email;

    private Address address;

    private Phone phone;

    private String fromAddressZip;
    
    private ShippingDetailsTimeframe timeframe;

    private ShippingDetailsMethods method;

    private Integer delay;

}

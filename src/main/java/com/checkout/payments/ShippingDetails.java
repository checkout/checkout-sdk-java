package com.checkout.payments;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ShippingDetails {

    private Address address;

    private Phone phone;

}

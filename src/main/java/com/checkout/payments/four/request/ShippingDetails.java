package com.checkout.payments.four.request;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class ShippingDetails {

    private Address address;

    private Phone phone;

}
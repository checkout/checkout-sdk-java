package com.checkout.payments.beta.request;

import com.checkout.common.beta.Address;
import com.checkout.common.beta.Phone;
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
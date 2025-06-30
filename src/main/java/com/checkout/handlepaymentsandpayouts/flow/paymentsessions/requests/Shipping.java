package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Shipping {

    /**
     * The shipping address
     */
    private Address address;

    /**
     * The phone number associated with the shipping address
     */
    private Phone phone;

}

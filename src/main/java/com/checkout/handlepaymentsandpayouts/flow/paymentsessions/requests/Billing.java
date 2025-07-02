package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Billing {

    /**
     * The billing address.
     */
    private Address address;

    /**
     * The phone number associated with the billing address
     */
    private Phone phone;

}

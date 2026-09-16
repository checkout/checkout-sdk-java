package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Details about the travel insurance purchased with the booking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AirlineInsurance {

    /**
     * The type of insurance purchased.
     * [Optional]
     */
    private String type;

    /**
     * The name of the insurance company.
     * [Optional]
     */
    private String company;

    /**
     * The price of the insurance.
     * [Optional]
     */
    private AirlineInsurancePrice price;
}

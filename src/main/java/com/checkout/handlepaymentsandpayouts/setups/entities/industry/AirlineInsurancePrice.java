package com.checkout.handlepaymentsandpayouts.setups.entities.industry;

import com.checkout.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The price of the travel insurance purchased with the booking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AirlineInsurancePrice {

    /**
     * The insurance amount.
     * [Optional]
     */
    private Double amount;

    /**
     * The 3-letter ISO currency code of the insurance amount.
     * [Optional]
     */
    private Currency currency;
}

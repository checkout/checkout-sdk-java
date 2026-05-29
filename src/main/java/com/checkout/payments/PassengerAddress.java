package com.checkout.payments;

import com.checkout.common.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains information about a passenger's address.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PassengerAddress {

    /**
     * The two-letter ISO country code of the passenger's country of residence.
     * [Optional]
     */
    private CountryCode country;
}

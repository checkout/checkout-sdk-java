package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.swish;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Swish account holder's details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SwishAccountHolder {

    /**
     * The account holder's first name.
     * [Optional]
     */
    private String firstName;

    /**
     * The account holder's last name.
     * [Optional]
     */
    private String lastName;
}

package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.p24;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The P24 account holder's details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class P24AccountHolder {

    /**
     * The account holder's name.
     * [Optional]
     * min 3 characters, max 100 characters
     */
    private String name;

    /**
     * The account holder's email address.
     * [Optional]
     */
    private String email;
}

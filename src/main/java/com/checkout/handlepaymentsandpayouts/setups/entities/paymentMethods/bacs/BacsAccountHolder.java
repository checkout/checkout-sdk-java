package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Bacs account holder details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BacsAccountHolder {

    /**
     * The type of account holder.
     * [Optional]
     */
    private BacsAccountHolderType type;

    /**
     * The first name of the account holder.
     * [Optional]
     */
    private String firstName;

    /**
     * The last name of the account holder.
     * [Optional]
     */
    private String lastName;

    /**
     * The legal name of a registered company that holds the account.
     * [Optional]
     */
    private String companyName;

    /**
     * The email address of the account holder.
     * [Optional]
     */
    private String email;
}

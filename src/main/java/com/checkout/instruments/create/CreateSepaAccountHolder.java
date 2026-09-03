package com.checkout.instruments.create;

import com.checkout.instruments.InstrumentAccountHolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder details of a SEPA instrument being stored.
 *
 * <p>Deliberately not {@link com.checkout.common.AccountHolder}, which is a superset carrying a
 * phone, an identification, a date of birth and a tax ID that this schema does not declare.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateSepaAccountHolder {

    /**
     * The first name of the account holder.
     * [Required]
     */
    private String firstName;

    /**
     * The last name of the account holder.
     * [Required]
     */
    private String lastName;

    /**
     * The billing address of the account holder.
     * [Required]
     */
    private CreateSepaBillingAddress billingAddress;

    /**
     * The legal name of a registered company that holds the account.
     * [Optional]
     * max 50 characters
     */
    private String companyName;

    /**
     * The type of account holder.
     * [Optional]
     */
    private InstrumentAccountHolderType type;

}

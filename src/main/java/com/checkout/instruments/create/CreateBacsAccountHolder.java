package com.checkout.instruments.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder details of a Bacs Direct Debit instrument being stored.
 *
 * <p>The store shape declares three properties only. The update and retrieve shapes add a company
 * name and an account holder type, so they use their own types.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateBacsAccountHolder {

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
    private CreateBacsBillingAddress billingAddress;

}

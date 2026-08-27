package com.checkout.instruments.get;

import com.checkout.instruments.InstrumentAccountHolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder's details of a stored Bacs Direct Debit instrument.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetBacsAccountHolder {

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
     * The legal name of a registered company that holds the account.
     * [Optional]
     * max 50 characters
     */
    private String companyName;

    /**
     * The billing address of the account holder.
     * [Required]
     */
    private GetBacsBillingAddress billingAddress;

    /**
     * The type of account holder.
     * [Optional]
     * Enum: "individual" "corporate"
     */
    private InstrumentAccountHolderType type;

}

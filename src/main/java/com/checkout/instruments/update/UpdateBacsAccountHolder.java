package com.checkout.instruments.update;

import com.checkout.instruments.InstrumentAccountHolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder details of a Bacs Direct Debit instrument being updated.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateBacsAccountHolder {

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
     * max 50 characters
     */
    private String companyName;

    /**
     * The billing address of the account holder.
     * [Optional]
     */
    private UpdateBacsBillingAddress billingAddress;

    /**
     * The type of account holder.
     * [Optional]
     * Enum: "individual" "corporate"
     */
    private InstrumentAccountHolderType type;

}

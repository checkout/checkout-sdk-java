package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The SEPA account holder details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SepaAccountHolder {

    /**
     * The type of account holder.
     * [Optional]
     */
    private SepaAccountHolderType type;

    /**
     * The first name of the account holder.
     * [Optional]
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The last name of the account holder.
     * [Optional]
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The legal name of a registered company that holds the account.
     * [Optional]
     */
    @SerializedName("company_name")
    private String companyName;
}

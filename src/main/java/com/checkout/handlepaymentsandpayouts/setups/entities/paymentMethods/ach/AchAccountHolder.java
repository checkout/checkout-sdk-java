package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The ACH account holder details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AchAccountHolder {

    /**
     * The type of account holder.
     * [Optional]
     */
    private AchAccountHolderType type;

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

    /**
     * The account holder's date of birth.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("date_of_birth")
    private String dateOfBirth;

    /**
     * The account holder's government document identification.
     * [Optional]
     */
    private AchIdentification identification;
}

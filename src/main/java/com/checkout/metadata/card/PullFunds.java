package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Describes whether the card is eligible to take funds from different accounts
 * to fund other non-merchant accounts (Account Funding Transactions).
 */
@Data
public final class PullFunds {

    /**
     * Describes whether the card is eligible for Account Funding Transactions between
     * accounts in different countries.
     * [Optional]
     */
    @SerializedName("cross_border")
    private Boolean crossBorder;

    /**
     * Describes whether the card is eligible for Account Funding Transactions between
     * accounts in the same country.
     * [Optional]
     */
    private Boolean domestic;
}

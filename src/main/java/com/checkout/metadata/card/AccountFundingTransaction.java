package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Account Funding Transaction (AFT) eligibility information for the card.
 */
@Data
public final class AccountFundingTransaction {

    /**
     * Describes whether the card is eligible to take funds from different accounts
     * to fund other non-merchant accounts.
     * [Optional]
     */
    @SerializedName("aft_indicator")
    private AftIndicator aftIndicator;
}

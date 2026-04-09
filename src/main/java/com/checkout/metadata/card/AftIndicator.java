package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Account Funding Transaction (AFT) eligibility indicator. Describes whether the card can be
 * used to take funds from different accounts to fund other non-merchant accounts.
 */
@Data
public final class AftIndicator {

    /**
     * Pull funds eligibility details — whether the card can be used for domestic and
     * cross-border Account Funding Transactions.
     * [Optional]
     */
    @SerializedName("pull_funds")
    private PullFunds pullFunds;
}

package com.checkout.metadata.card;

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
    private AftIndicator aftIndicator;
}

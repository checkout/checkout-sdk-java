package com.checkout.metadata.card;

import lombok.Data;

/**
 * Card payout eligibility details. Present in the response only when the request
 * {@code format} is {@code card_payouts}.
 */
@Data
public final class CardMetadataPayouts {

    /**
     * Describes whether the card is eligible for domestic non-money transfer transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    private PayoutsTransactionsType domesticNonMoneyTransfer;

    /**
     * Describes whether the card is eligible for cross-border non-money transfer transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    private PayoutsTransactionsType crossBorderNonMoneyTransfer;

    /**
     * Describes whether the card is eligible for domestic gambling transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    private PayoutsTransactionsType domesticGambling;

    /**
     * Describes whether the card is eligible for cross-border gambling transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    private PayoutsTransactionsType crossBorderGambling;

    /**
     * Describes whether the card is eligible for domestic money transfer transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    private PayoutsTransactionsType domesticMoneyTransfer;

    /**
     * Describes whether the card is eligible for cross-border money transfer transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    private PayoutsTransactionsType crossBorderMoneyTransfer;
}

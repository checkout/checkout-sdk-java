package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("domestic_non_money_transfer")
    private PayoutsTransactionsType domesticNonMoneyTransfer;

    /**
     * Describes whether the card is eligible for cross-border non-money transfer transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    @SerializedName("cross_border_non_money_transfer")
    private PayoutsTransactionsType crossBorderNonMoneyTransfer;

    /**
     * Describes whether the card is eligible for domestic gambling transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    @SerializedName("domestic_gambling")
    private PayoutsTransactionsType domesticGambling;

    /**
     * Describes whether the card is eligible for cross-border gambling transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    @SerializedName("cross_border_gambling")
    private PayoutsTransactionsType crossBorderGambling;

    /**
     * Describes whether the card is eligible for domestic money transfer transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    @SerializedName("domestic_money_transfer")
    private PayoutsTransactionsType domesticMoneyTransfer;

    /**
     * Describes whether the card is eligible for cross-border money transfer transactions.
     * [Optional]
     * Enum: "not_supported" "standard" "fast_funds" "unknown"
     */
    @SerializedName("cross_border_money_transfer")
    private PayoutsTransactionsType crossBorderMoneyTransfer;
}

package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class CardMetadataPayouts {

    @SerializedName("domestic_non_money_transfer")
    private PayoutsTransactionsType domesticNonMoneyTransfer;

    @SerializedName("cross_border_non_money_transfer")
    private PayoutsTransactionsType crossBorderNonMoneyTransfer;

    @SerializedName("domestic_gambling")
    private PayoutsTransactionsType domesticGambling;

    @SerializedName("cross_border_gambling")
    private PayoutsTransactionsType crossBorderGambling;

    @SerializedName("domestic_money_transfer")
    private PayoutsTransactionsType domesticMoneyTransfer;

    @SerializedName("cross_border_money_transfer")
    private PayoutsTransactionsType crossBorderMoneyTransfer;
}

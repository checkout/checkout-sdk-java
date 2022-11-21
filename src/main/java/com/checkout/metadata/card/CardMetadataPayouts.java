package com.checkout.metadata.card;

import lombok.Data;

@Data
public final class CardMetadataPayouts {

    private PayoutsTransactionsType domesticNonMoneyTransfer;

    private PayoutsTransactionsType crossBorderNonMoneyTransfer;

    private PayoutsTransactionsType domesticGambling;

    private PayoutsTransactionsType crossBorderGambling;

    private PayoutsTransactionsType domesticMoneyTransfer;

    private PayoutsTransactionsType crossBorderMoneyTransfer;
}

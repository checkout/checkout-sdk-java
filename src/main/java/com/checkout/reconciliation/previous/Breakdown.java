package com.checkout.reconciliation.previous;

import lombok.Data;

@Data
public final class Breakdown {

    private String type;

    private String date;

    private String processingCurrencyAmount;

    private String payoutCurrencyAmount;

}

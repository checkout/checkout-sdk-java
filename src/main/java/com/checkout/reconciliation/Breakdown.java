package com.checkout.reconciliation;

import lombok.Data;

import java.time.Instant;

@Data
public class Breakdown {
    private String type;
    private Instant date;
    private String processingCurrencyAmount;
    private String payoutCurrencyAmount;
}

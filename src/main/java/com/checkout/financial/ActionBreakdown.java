package com.checkout.financial;

import com.checkout.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ActionBreakdown {

    private String breakdownType;

    private Double fxRateApplied;

    private Currency holdingCurrency;

    private Double holdingCurrencyAmount;

    private Currency processingCurrency;

    private Double processingCurrencyAmount;

    private Currency transactionCurrency;

    private Double transactionCurrencyAmount;

    private Double processingToTransactionCurrencyFxRate;

    private Double transactionToHoldingCurrencyFxRate;

    private String feeDetail;

    private String reserveRate;

    private Instant reserveReleaseDate;

    private Instant reserveDeductedDate;

    private Double taxFxRate;

    private Currency entityCountryTaxCurrency;

    private Double taxCurrencyAmount;
}

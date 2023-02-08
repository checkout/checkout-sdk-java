package com.checkout.financial;

import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionBreakdown {

    @SerializedName("breakdown_type")
    private String breakdownType;

    @SerializedName("fx_rate_applied")
    private Double fxRateApplied;

    @SerializedName("holding_currency")
    private Currency holdingCurrency;

    @SerializedName("holding_currency_amount")
    private Double holdingCurrencyAmount;

    @SerializedName("processing_currency")
    private Currency processingCurrency;

    @SerializedName("processing_currency_amount")
    private Double processingCurrencyAmount;

    @SerializedName("transaction_currency")
    private Currency transactionCurrency;

    @SerializedName("transaction_currency_account")
    private Double transactionCurrencyAccount;

    @SerializedName("processing_to_transaction_currency_fx_rate")
    private Double processingToTransactionCurrencyFxRate;

    @SerializedName("transaction_to_holding_currency_fx_rate")
    private Double transactionToHoldingCurrencyFxRate;

    @SerializedName("fee_detail")
    private String feeDetail;

    @SerializedName("reserve_rate")
    private String reserveRate;

    @SerializedName("reserve_release_date")
    private Instant reserveReleaseDate;

    @SerializedName("reserve_deducted_date")
    private Instant reserveDeductedDate;

    @SerializedName("tax_fx_rate")
    private Double taxFxRate;

    @SerializedName("entity_country_tax_currency")
    private Currency entityCountryTaxCurrency;

    @SerializedName("tax_currency_amount")
    private Double taxCurrencyAmount;
}

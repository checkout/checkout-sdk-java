package com.checkout.balances;

import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class CurrencyAccountBalance {

    private String descriptor;

    @SerializedName("holding_currency")
    private Currency holdingCurrency;

    private Balances balances;

}

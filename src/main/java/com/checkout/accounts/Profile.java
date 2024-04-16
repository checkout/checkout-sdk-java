package com.checkout.accounts;

import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Profile {

    private List<String> urls;

    private List<String> mccs;

    @SerializedName("default_holding_currency")
    private Currency defaultHoldingCurrency;

    @SerializedName("holding_currencies")
    private List<Currency> holdingCurrencies;

}

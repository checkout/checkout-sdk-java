package com.checkout.accounts;

import com.checkout.common.Currency;
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

    private Currency defaultHoldingCurrency;

    private List<Currency> holdingCurrencies;

}

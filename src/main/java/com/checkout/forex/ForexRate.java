package com.checkout.forex;

import lombok.Data;

@Data
public final class ForexRate {

    private Double exchangeRate;

    private String currencyPair;
}

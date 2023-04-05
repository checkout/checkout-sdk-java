package com.checkout.forex;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ForexRate {

    @SerializedName("exchange_rate")
    private Double exchangeRate;

    @SerializedName("currency_pair")
    private String currencyPair;
}

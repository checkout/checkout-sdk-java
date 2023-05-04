package com.checkout.forex;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RatesQueryResponse extends HttpMetadata {

    private String product;

    private ForexSource source;

    private List<ForexRate> rates;

    @SerializedName("invalid_currency_pairs")
    private List<String> invalidCurrencyPairs;
}

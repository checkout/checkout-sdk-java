package com.checkout.forex;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RatesQueryResponse extends HttpMetadata {

    private String product;

    private ForexSource source;

    private List<ForexRate> rates;

    private List<String> invalidCurrencyPairs;
}

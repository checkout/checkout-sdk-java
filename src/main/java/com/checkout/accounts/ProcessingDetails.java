package com.checkout.accounts;

import com.checkout.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProcessingDetails {

    private String settlementCountry;

    private List<String> targetCountries;

    private Integer annualProcessingVolume;

    private Integer averageTransactionValue;

    private Integer highestTransactionValue;

    private Currency currency;

}
